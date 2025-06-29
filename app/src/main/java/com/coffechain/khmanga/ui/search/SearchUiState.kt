package com.coffechain.khmanga.ui.search

import com.coffechain.khmanga.domain.repo.SearchResult

sealed interface SearchUiState {
    data object Idle : SearchUiState
    data object Loading : SearchUiState
    data class Success(val results: SearchResult) : SearchUiState
    data object Empty : SearchUiState
    data class Error(val message: String) : SearchUiState
}
