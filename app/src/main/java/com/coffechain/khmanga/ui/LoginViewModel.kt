package com.coffechain.khmanga.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coffechain.khmanga.domain.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    // ... State untuk UI ...

    // Fungsi ini dipanggil dari UI setelah mendapatkan idToken dari Google
    fun onGoogleSignInResult(idToken: String?) {
        if (idToken == null) {
            // Handle error, tampilkan pesan ke pengguna
            // uiState.value = LoginState.Error("Google Sign-In failed.")
            return
        }
        viewModelScope.launch {
            // uiState.value = LoginState.Loading
            val result = authRepository.signInWithGoogle(idToken)
            result.fold(
                onSuccess = { user -> /* ... handle sukses ... */ },
                onFailure = { e -> /* ... handle error ... */ }
            )
        }
    }

    fun onAnonymousSignInClicked() {
        viewModelScope.launch {
            // uiState.value = LoginState.Loading
            val result = authRepository.signInAnonymously()
            result.fold(
                onSuccess = { user -> /* ... handle sukses ... */ },
                onFailure = { e -> /* ... handle error ... */ }
            )
        }
    }
}