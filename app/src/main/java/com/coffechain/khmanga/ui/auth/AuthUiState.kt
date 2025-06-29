package com.coffechain.khmanga.ui.auth

import com.google.firebase.auth.FirebaseUser

sealed interface AuthUiState {
    data object Idle : AuthUiState          // Keadaan awal, belum ada aksi
    data object Loading : AuthUiState       // Saat proses login sedang berjalan
    data class Success(val user: FirebaseUser) : AuthUiState // Login berhasil
    data class Error(val message: String) : AuthUiState     // Terjadi error
}