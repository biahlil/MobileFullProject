package com.coffechain.khmanga.domain.repo

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    fun getCurrentUser(): FirebaseUser?
    suspend fun signInWithEmailPassword(email: String, pass: String): Result<FirebaseUser>
    suspend fun signUpWithEmailPassword(email: String, pass: String): Result<FirebaseUser>
    suspend fun logout()

    /**
     * Login dengan Google menggunakan idToken yang didapat dari Google Sign-In flow.
     */
    suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser>

    /**
     * Login secara anonim.
     */
    suspend fun signInAnonymously(): Result<FirebaseUser>
}