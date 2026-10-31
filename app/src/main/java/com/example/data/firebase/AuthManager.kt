package com.example.data.firebase

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import com.example.R
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthManager(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {
    companion object {
        private const val TAG = "AuthManager"
    }

    val currentUser: FirebaseUser?
        get() = auth.currentUser

    fun authStateFlow(): Flow<FirebaseUser?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            trySend(firebaseAuth.currentUser)
        }
        auth.addAuthStateListener(listener)
        trySend(auth.currentUser)
        awaitClose { auth.removeAuthStateListener(listener) }
    }

    suspend fun signInWithGoogle(context: Context): Result<FirebaseUser> = withContext(Dispatchers.Main) {
        try {
            val webClientId = context.getString(R.string.default_web_client_id)
            val credentialManager = CredentialManager.create(context)

            val googleIdOption = GetSignInWithGoogleOption.Builder(webClientId)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val result = credentialManager.getCredential(
                request = request,
                context = context
            )

            val credential = result.credential
            if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val authCredential = GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)
                val authResult = auth.signInWithCredential(authCredential).await()
                val user = authResult.user
                if (user != null) {
                    Result.success(user)
                } else {
                    Result.failure(IllegalStateException("FirebaseUser is null after successful sign-in"))
                }
            } else {
                Result.failure(IllegalStateException("Unsupported credential type returned: ${credential.type}"))
            }
        } catch (e: GetCredentialCancellationException) {
            Log.w(TAG, "Interactive Google sign-in was cancelled by the user", e)
            Result.failure(e)
        } catch (e: GetCredentialException) {
            Log.e(TAG, "Credential Manager failure during Google sign-in", e)
            Result.failure(e)
        } catch (e: Exception) {
            Log.e(TAG, "Firebase authentication failed with Google credential", e)
            Result.failure(e)
        }
    }

    fun signOut() {
        auth.signOut()
    }
}
