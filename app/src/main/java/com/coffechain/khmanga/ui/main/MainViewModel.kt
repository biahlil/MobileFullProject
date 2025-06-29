package com.coffechain.khmanga.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cloudinary.android.MediaManager
import com.coffechain.khmanga.domain.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val userProfileState: StateFlow<UserProfileState> = userRepository.observeUserProfile()
        .map { user ->
            if (user != null) {
                // Jika user ada, buat URL gambar dari Cloudinary
                val photoUrl = user.photoUrl?.let { publicId ->
                    val baseBuilder = MediaManager.get().url().generate(publicId)
                    "$baseBuilder?w=400&h=400&c=fill&g=face"
                }
                UserProfileState.Success(displayName = user.displayName, photoUrl = photoUrl)
            } else {
                UserProfileState.Loading
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserProfileState.Loading
        )
}

