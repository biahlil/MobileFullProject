package com.coffechain.khmanga.domain.repo

import android.content.Context
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    fun getCurrentUser(): FirebaseUser?
    suspend fun logout(context: Context): Result<Unit>
}