package com.coffechain.khmanga.ui.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coffechain.khmanga.domain.model.User
import com.coffechain.khmanga.domain.repo.AuthRepository
import com.coffechain.khmanga.domain.repo.UserRepository
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState = _uiState.asStateFlow()

    fun getCurrentUser(): FirebaseUser? {
        return authRepository.getCurrentUser()
    }

    fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        _uiState.update { AuthUiState.Loading }

        val response = result.idpResponse
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            val firebaseUser = getCurrentUser()!!
            Timber.i("Sign-in berhasil dari ViewModel.")

            if (response?.isNewUser == true) {
                val newUser = User(
                    uid = firebaseUser.uid,
                    displayName = firebaseUser.displayName,
                    email = firebaseUser.email,
                    photoUrl = firebaseUser.photoUrl?.toString()
                )
                createUserProfile(newUser)
            } else {
                _uiState.update { AuthUiState.Success(firebaseUser) }
            }
        } else {
            val errorMessage = response?.error?.message ?: "Sign-in dibatalkan"
            _uiState.update { AuthUiState.Error(errorMessage) }
            Timber.e("Sign-in gagal: $errorMessage")
        }
    }

    private fun createUserProfile(user: User) {
        viewModelScope.launch {
            userRepository.createUserProfile(user)
                .onSuccess {
                    Timber.i("Profil Firestore berhasil dibuat untuk user: ${user.uid}")
                    _uiState.update { AuthUiState.Success(getCurrentUser()!!) }
                }
                .onFailure { exception ->
                    Timber.e(exception, "Gagal membuat profil Firestore.")
                    _uiState.update { AuthUiState.Error(exception.message ?: "Gagal menyimpan profil.") }
                }
        }
    }
}