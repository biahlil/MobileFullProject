package com.coffechain.khmanga.ui.cafeselect

import com.coffechain.khmanga.domain.model.Cafe

sealed interface CafeSelectUiState {
    data object Loading : CafeSelectUiState
    data class Success(
        val popularCafes: List<Cafe>,
        val nearbyCafes: List<Cafe>
    ) : CafeSelectUiState
    data class Error(val message: String) : CafeSelectUiState
}