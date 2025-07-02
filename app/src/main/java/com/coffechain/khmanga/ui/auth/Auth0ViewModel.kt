package com.coffechain.khmanga.ui.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.result.Credentials
import com.coffechain.khmanga.domain.repo.Auth0Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Auth0ViewModel @Inject constructor(
    private val auth0Repository: Auth0Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow<Auth0UiState>(Auth0UiState.Idle)
    val uiState = _uiState.asStateFlow()

    // Fungsi ini dipanggil dari UI untuk memulai login
    fun login(context: Context) {
        _uiState.update { Auth0UiState.Loading }
        auth0Repository.login(
            context = context,
            onSuccess = { credentials ->
                // Setelah login berhasil, ambil profil pengguna
                fetchUserProfile(credentials)
            },
            onFailure = { error ->
                _uiState.update { Auth0UiState.Error(error.getDescription() ?: "Login gagal") }
            }
        )
    }

    // Fungsi untuk logout
    fun logout(context: Context) {
        _uiState.update { Auth0UiState.Loading }
        auth0Repository.logout(
            context = context,
            onSuccess = {
                _uiState.update { Auth0UiState.Idle } // Kembali ke state awal
            },
            onFailure = { error ->
                _uiState.update { Auth0UiState.Error(error.getDescription() ?: "Logout gagal") }
            }
        )
    }

    // Mengambil data profil setelah mendapatkan token
    private fun fetchUserProfile(credentials: Credentials) {
        viewModelScope.launch {
            val accessToken = credentials.accessToken
            auth0Repository.getUserProfile(accessToken)
                .onSuccess { userProfile ->
                    _uiState.update { Auth0UiState.Success(userProfile) }
                }
                .onFailure { error ->
                    _uiState.update { Auth0UiState.Error(error.message ?: "Gagal mengambil profil") }
                }
        }
    }
}