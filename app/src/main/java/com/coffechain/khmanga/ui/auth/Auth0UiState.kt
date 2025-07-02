package com.coffechain.khmanga.ui.auth

import com.auth0.android.result.UserProfile

sealed interface Auth0UiState {
    data object Idle : Auth0UiState
    data object Loading : Auth0UiState
    data class Success(val userProfile: UserProfile) : Auth0UiState
    data class Error(val message: String) : Auth0UiState
}