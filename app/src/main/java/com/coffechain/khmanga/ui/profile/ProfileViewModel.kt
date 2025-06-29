package com.coffechain.khmanga.ui.profile

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coffechain.khmanga.domain.model.User
import com.coffechain.khmanga.domain.repo.AuthRepository
import com.coffechain.khmanga.domain.repo.CafeRepository
import com.coffechain.khmanga.domain.repo.StorageRepository
import com.coffechain.khmanga.domain.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val storageRepository: StorageRepository,
    private val cafeRepository: CafeRepository
) : ViewModel() {

    val userProfile: StateFlow<User?> = userRepository.observeUserProfile()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    private val _imageUploadState = MutableStateFlow<ImageUploadState>(ImageUploadState.Idle)
    val imageUploadState: StateFlow<ImageUploadState> = _imageUploadState.asStateFlow()

    init {
        viewModelScope.launch {
            userRepository.syncUserProfile()
        }
    }

    fun onProfileImageSelected(imageUri: Uri?) {
        if (imageUri == null) return

        val userId = authRepository.getCurrentUser()?.uid ?: return

        viewModelScope.launch {
            _imageUploadState.value = ImageUploadState.Uploading

            storageRepository.uploadUserProfileImage(userId, imageUri)
                .onSuccess { publicId ->
                    Timber.tag("ProfileViewModel").i("Gambar berhasil diunggah, public_id: $publicId")

                    userRepository.updateUserPhotoUrl(userId, publicId)
                        .onSuccess {
                            Timber.tag("ProfileViewModel").i("URL/public_id berhasil disimpan di database.")
                            _imageUploadState.value = ImageUploadState.Success
                        }
                        .onFailure { exception ->
                            _imageUploadState.value = ImageUploadState.Error(exception.message ?: "Gagal menyimpan URL.")
                        }
                }
                .onFailure { exception ->
                    Timber.tag("ProfileViewModel").e(exception, "Gagal mengunggah foto profil.")
                    _imageUploadState.value = ImageUploadState.Error(exception.message ?: "Gagal mengunggah gambar.")
                }
        }
    }

    fun logout(context: Context) {
        viewModelScope.launch {
            userRepository.clearLocalUser()
            authRepository.logout(context)
                .onSuccess {
                    Timber.i("Logout berhasil.")
                }
                .onFailure { exception ->
                    Timber.e(exception, "Logout gagal.")
                }
        }
    }

    fun seedDatabase() {
        viewModelScope.launch {
            // Asumsikan cafeRepository sudah di-inject
            cafeRepository.seedDatabase() // Panggil fungsi yang baru
                .onSuccess {
                    Timber.d("Database seeding lengkap dipicu dan berhasil.")
                }
                .onFailure { e -> Timber.e(e, "Database seeding lengkap gagal.") }
        }
    }
}