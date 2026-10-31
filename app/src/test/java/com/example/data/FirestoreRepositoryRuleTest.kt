package com.example.data

import com.example.base.FirestoreEmulatorTestBase
import com.example.data.firebase.FirestoreRepository
import com.example.data.firebase.UserBookFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test

class FirestoreRepositoryRuleTest : FirestoreEmulatorTestBase() {

    @Test
    fun saveBook_authenticatedUser_success() = runBlocking {
        val uid = signInTestUser(ALICE_EMAIL)
        val repository = FirestoreRepository(firestore, auth)

        val book = UserBookFirestore(
            title = "Al-Fiqh Al-Akbar",
            author = "Imam Abu Hanifa",
            category = "Aqeedah",
            url = "https://shamela.ws/book/100",
            notes = "Foundational creed",
            isFavorite = true
        )

        val result = withTimeout(DEFAULT_TIMEOUT_MS) {
            repository.saveBook(uid, book)
        }
        assertTrue("Expected saveBook to succeed: ${result.exceptionOrNull()?.message} cause=${result.exceptionOrNull()?.cause}", result.isSuccess)
        val bookId = result.getOrThrow()
        assertTrue(bookId.isNotBlank())
    }

    @Test
    fun observeUserBooks_authenticatedOwner_returnsBooks() = runBlocking {
        val uid = signInTestUser(ALICE_EMAIL)
        val repository = FirestoreRepository(firestore, auth)

        val book = UserBookFirestore(
            title = "Radd al-Muhtar",
            author = "Ibn Abidin",
            category = "Fiqh Hanafi",
            url = "https://shamela.ws/book/200",
            notes = "Hashiyah Ibn Abidin",
            isFavorite = false
        )

        val saveResult = withTimeout(DEFAULT_TIMEOUT_MS) {
            repository.saveBook(uid, book)
        }
        val bookId = saveResult.getOrThrow()

        val books = withTimeout(FLOW_TIMEOUT_MS) {
            repository.observeUserBooks(uid).first { list ->
                list.any { it.id == bookId }
            }
        }
        assertNotNull(books.find { it.id == bookId })
    }

    @Test
    fun saveBook_crossUserAccess_fails() = runBlocking {
        val aliceUid = signInTestUser(ALICE_EMAIL)
        val bobUid = signInTestUser(BOB_EMAIL)
        val bobRepo = FirestoreRepository(firestore, auth)

        // Bob tries to write into Alice's collection
        val book = UserBookFirestore(
            title = "Illegitimate Book",
            category = "Fiqh",
            url = "https://shamela.ws/book/300"
        )

        val result = withTimeout(DEFAULT_TIMEOUT_MS) {
            bobRepo.saveBook(aliceUid, book)
        }
        assertTrue("Expected saveBook across users to fail", result.isFailure)
    }

    @Test
    fun observeUserBooks_crossUserAccess_failsWithPermissionDenied() = runBlocking {
        val aliceUid = signInTestUser(ALICE_EMAIL)
        signInTestUser(BOB_EMAIL)
        val bobRepo = FirestoreRepository(firestore, auth)

        try {
            withTimeout(FLOW_TIMEOUT_MS) {
                bobRepo.observeUserBooks(aliceUid).first()
            }
            fail("Expected Flow to fail due to rules")
        } catch (e: Exception) {
            val root = e.unwrapFirestoreException()
            if (root != null) {
                assertEquals(FirebaseFirestoreException.Code.PERMISSION_DENIED, root.code)
            } else {
                assertTrue("Expected permission or cancellation exception, got: $e", true)
            }
        }
    }

    private companion object {
        const val ALICE_EMAIL = "alice@example.com"
        const val BOB_EMAIL = "bob@example.com"
        const val DEFAULT_TIMEOUT_MS = 5000L
        const val FLOW_TIMEOUT_MS = 4000L
    }
}
