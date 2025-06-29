package com.coffechain.khmanga.data.repository

import android.net.Uri
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.coffechain.khmanga.domain.repo.StorageRepository
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

@Singleton
class StorageRepositoryImpl @Inject constructor() : StorageRepository {

    override suspend fun uploadUserProfileImage(userId: String, imageUri: Uri): Result<String> {
        return try {
            val publicId = "photoProfile/$userId"

            val uploadedPublicId = suspendCancellableCoroutine<String> { continuation ->
                MediaManager.get().upload(imageUri)
                    .option("public_id", publicId)
                    .option("overwrite", true)
                    .callback(object : UploadCallback {
                        override fun onStart(requestId: String) {
                            Timber.tag("StorageRepository").d("Cloudinary upload started...")
                        }
                        override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {}

                        override fun onSuccess(requestId: String, resultData: Map<*, *>) {
                            val successPublicId = resultData["public_id"] as String
                            Timber.tag("StorageRepository").i("Cloudinary upload success. Public ID: $successPublicId")
                            continuation.resume(successPublicId)
                        }

                        override fun onError(requestId: String, error: ErrorInfo) {
                            Timber.tag("StorageRepository").e("Cloudinary upload error: ${error.description}")
                            continuation.cancel(Exception(error.description))
                        }

                        override fun onReschedule(requestId: String, error: ErrorInfo) {}
                    }).dispatch()

                continuation.invokeOnCancellation {
                    MediaManager.get().cancelRequest(continuation.toString())
                }
            }
            Result.success(uploadedPublicId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}