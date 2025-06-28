package com.coffechain.khmanga.data.repository

import com.coffechain.khmanga.data.local.dao.UserDao
import com.coffechain.khmanga.data.local.entity.UserEntity
import com.coffechain.khmanga.data.local.toDomainModel
import com.coffechain.khmanga.data.local.toEntity
import com.coffechain.khmanga.domain.model.User
import com.coffechain.khmanga.domain.repo.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val db: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : UserRepository {

    override suspend fun createUserProfile(user: User): Result<Unit> {
        return try {
            val userMap = mapOf(
                "displayName" to user.displayName,
                "email" to user.email,
                "photoUrl" to user.photoUrl,
                "createdAt" to FieldValue.serverTimestamp()
            )

            db.collection("users").document(user.uid).set(userMap).await()
            val userEntity = user.toEntity()
            userDao.insertOrUpdateUser(userEntity)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun observeUserProfile(): Flow<User?> {
        val uid = firebaseAuth.currentUser?.uid ?: return kotlinx.coroutines.flow.flowOf(null)

        return userDao.observeUserById(uid).map { userEntity ->
            userEntity?.toDomainModel()
        }
    }

    override suspend fun syncUserProfile(): Result<Unit> {
        val uid = firebaseAuth.currentUser?.uid
        if (uid == null) {
            return Result.success(Unit)
        }

        return try {
            val documentSnapshot = db.collection("users").document(uid).get().await()

            if (documentSnapshot.exists()) {
                val userEntity = UserEntity(
                    uid = documentSnapshot.id,
                    displayName = documentSnapshot.getString("displayName"),
                    email = documentSnapshot.getString("email"),
                    photoUrl = documentSnapshot.getString("photoUrl")
                )
                userDao.insertOrUpdateUser(userEntity)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun clearLocalUser() {
        userDao.clearAll()
    }
}