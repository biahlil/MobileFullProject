package com.coffechain.khmanga.data.repository

import com.coffechain.khmanga.domain.model.User
import com.coffechain.khmanga.domain.repo.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : UserRepository {
    override suspend fun getUserProfile(uid: String): Result<User?> {
        TODO("Not yet implemented")
    }

    override suspend fun createUserProfile(user: User): Result<Unit> {
        TODO("Not yet implemented")
    }

}