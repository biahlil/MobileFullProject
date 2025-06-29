package com.coffechain.khmanga.domain.repo

import android.net.Uri

interface StorageRepository {
    suspend fun uploadUserProfileImage(userId: String, imageUri: Uri): Result<String>
}