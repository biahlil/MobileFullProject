package com.coffechain.khmanga.ui.detail

import com.coffechain.khmanga.domain.model.Cafe
import com.coffechain.khmanga.domain.model.Food
import com.coffechain.khmanga.domain.model.Manga

sealed interface CafeDetailUiState {
    data object Loading : CafeDetailUiState
    data class Success(
        val cafe: Cafe,
        val mangaList: List<Manga>,
        val foodMenu: List<Food>,
    ) : CafeDetailUiState
    data class Error(val message: String) : CafeDetailUiState
}
