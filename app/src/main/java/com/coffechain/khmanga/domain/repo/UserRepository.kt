package com.coffechain.khmanga.domain.repo

import com.coffechain.khmanga.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun syncUserProfile(): Result<Unit>
    suspend fun clearLocalUser()
    suspend fun createUserProfile(user: User): Result<Unit>
    fun observeUserProfile(): Flow<User?>
    suspend fun updateUserPhotoUrl(userId: String, newPhotoUrl: String): Result<Unit>

}
