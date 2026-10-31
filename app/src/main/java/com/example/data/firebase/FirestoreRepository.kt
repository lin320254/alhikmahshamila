package com.example.data.firebase

import android.content.Context
import com.example.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirestoreRepository(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {
    // Secondary constructor resolving named database ID from string resources
    constructor(context: Context) : this(
        FirebaseFirestore.getInstance(
            context.applicationContext.getString(R.string.firestore_database_id)
        )
    )

    private fun requireUserId(userId: String): String {
        val currentUid = auth.currentUser?.uid
        checkNotNull(currentUid) { "User must be authenticated to perform Firestore operations" }
        require(userId == currentUid) { "Operation denied: user ID mismatch" }
        return currentUid
    }

    suspend fun syncUserProfile(user: FirebaseUser): Result<Unit> = withContext(Dispatchers.IO) {
        val path = "users/${user.uid}"
        try {
            val userRef = db.collection("users").document(user.uid)
            val snapshot = userRef.get().await()
            if (!snapshot.exists()) {
                val profileMap = mapOf(
                    "userId" to user.uid,
                    "displayName" to (user.displayName ?: user.email?.substringBefore("@") ?: "User"),
                    "email" to (user.email ?: ""),
                    "createdAt" to FieldValue.serverTimestamp(),
                    "lastLoginAt" to FieldValue.serverTimestamp()
                )
                userRef.set(profileMap).await()
            } else {
                val updateMap = mapOf<String, Any>(
                    "lastLoginAt" to FieldValue.serverTimestamp()
                )
                userRef.set(updateMap, SetOptions.merge()).await()
            }
            Result.success(Unit)
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.WRITE, path)
            Result.failure(e)
        }
    }

    fun observeUserBooks(userId: String): Flow<List<UserBookFirestore>> = callbackFlow {
        val path = "users/$userId/books"
        val collection = db.collection("users").document(userId).collection("books")
        val listener = collection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                handleFirestoreError(error, OperationType.LIST, path)
                close(error)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val items = snapshot.documents.mapNotNull { doc ->
                    val item = doc.toObject(UserBookFirestore::class.java)
                    item?.copy(id = doc.id)
                }
                trySend(items)
            }
        }
        awaitClose { listener.remove() }
    }

    suspend fun saveBook(userId: String, book: UserBookFirestore): Result<String> = withContext(Dispatchers.IO) {
        val isNew = book.id.isBlank()
        try {
            val uid = requireUserId(userId)
            val booksCol = db.collection("users").document(uid).collection("books")
            val docRef = if (isNew) booksCol.document() else booksCol.document(book.id)

            val writePayload = mutableMapOf<String, Any>(
                "id" to docRef.id,
                "userId" to uid,
                "title" to book.title,
                "category" to book.category,
                "url" to book.url,
                "isFavorite" to book.isFavorite,
                "createdAt" to (if (isNew) FieldValue.serverTimestamp() else (book.createdAt ?: FieldValue.serverTimestamp()))
            )

            if (book.author.isNotBlank()) {
                writePayload["author"] = book.author
            }
            if (book.notes.isNotBlank()) {
                writePayload["notes"] = book.notes
            }
            if (!isNew) {
                writePayload["updatedAt"] = FieldValue.serverTimestamp()
            }

            docRef.set(writePayload).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            handleFirestoreError(e, if (isNew) OperationType.CREATE else OperationType.UPDATE, "users/$userId/books/${book.id}")
            Result.failure(e)
        }
    }

    suspend fun deleteBook(userId: String, bookId: String): Result<Unit> = withContext(Dispatchers.IO) {
        val path = "users/$userId/books/$bookId"
        try {
            val uid = requireUserId(userId)
            val docRef = db.collection("users").document(uid).collection("books").document(bookId)
            docRef.delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.DELETE, path)
            Result.failure(e)
        }
    }

    suspend fun setFavorite(userId: String, bookId: String, isFavorite: Boolean): Result<Unit> = withContext(Dispatchers.IO) {
        val path = "users/$userId/books/$bookId"
        try {
            val uid = requireUserId(userId)
            val docRef = db.collection("users").document(uid).collection("books").document(bookId)
            val snapshot = docRef.get().await()
            val existing = snapshot.toObject(UserBookFirestore::class.java)
            if (existing != null) {
                val updatePayload = mapOf<String, Any>(
                    "isFavorite" to isFavorite,
                    "updatedAt" to FieldValue.serverTimestamp()
                )
                docRef.set(updatePayload, SetOptions.merge()).await()
            }
            Result.success(Unit)
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.UPDATE, path)
            Result.failure(e)
        }
    }
}
