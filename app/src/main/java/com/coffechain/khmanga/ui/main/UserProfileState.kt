package com.coffechain.khmanga.ui.main

sealed interface UserProfileState {
    data object Loading : UserProfileState
    data class Success(val displayName: String?, val photoUrl: String?) : UserProfileState
}