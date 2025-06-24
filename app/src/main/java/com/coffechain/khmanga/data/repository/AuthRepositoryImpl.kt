package com.coffechain.khmanga.data.repository

import com.coffechain.khmanga.domain.repo.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {
    override fun getCurrentUser(): FirebaseUser? {
        TODO("Not yet implemented")
    }

    override suspend fun signInWithEmailPassword(
        email: String,
        pass: String
    ): Result<FirebaseUser> {
        TODO("Not yet implemented")
    }

    override suspend fun signUpWithEmailPassword(
        email: String,
        pass: String
    ): Result<FirebaseUser> {
        TODO("Not yet implemented")
    }

    override suspend fun logout() {
        TODO("Not yet implemented")
    }

    // ... implementasi fungsi lain (getCurrentUser, signInWithEmailPassword, dll.) ...

    override suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser> {
        return try {
            // Buat kredensial dari idToken
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            // Lakukan sign in ke Firebase dengan kredensial tersebut
            val authResult = auth.signInWithCredential(credential).await()
            Result.success(authResult.user!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signInAnonymously(): Result<FirebaseUser> {
        return try {
            val authResult = auth.signInAnonymously().await()
            Result.success(authResult.user!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}