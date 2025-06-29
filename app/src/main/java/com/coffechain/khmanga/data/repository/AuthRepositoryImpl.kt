package com.coffechain.khmanga.data.repository

import android.content.Context
import com.coffechain.khmanga.domain.repo.AuthRepository
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {

    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    override suspend fun logout(context: Context): Result<Unit> {
        return try {
            val logoutResult = AuthUI.getInstance().signOut(context).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}