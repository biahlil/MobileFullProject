package com.coffechain.khmanga.domain.repo

import com.coffechain.khmanga.domain.model.User

interface UserRepository {
//    Mengambil profil pengguna berdasarkan UID dari Firebase Auth.
    suspend fun getUserProfile(uid: String): Result<User?> // Bisa null jika tidak ditemukan
    suspend fun createUserProfile(user: User): Result<Unit>
}
