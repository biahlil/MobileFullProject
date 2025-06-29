package com.coffechain.khmanga.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coffechain.khmanga.domain.repo.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val uiState = _uiState.asStateFlow()

    init {
        // Debounce: Hanya jalankan pencarian 500ms setelah pengguna berhenti mengetik
        viewModelScope.launch {
            _searchQuery
                .debounce(500L)
                .filter { it.isNotBlank() && it.length > 2 }
                .distinctUntilChanged()
                .collect { query ->
                    performSearch(query)
                }
            Timber.tag("DebugCafeDao").d("SearchViewModel: $searchQuery")
        }
    }

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
        if (newQuery.isBlank()) {
            _uiState.value = SearchUiState.Idle
        }
    }

    private fun performSearch(query: String) {
        viewModelScope.launch {
            _uiState.value = SearchUiState.Loading
            searchRepository.searchEverything(query)
                .onSuccess { searchResult ->
                    if (searchResult.cafes.isNotEmpty() || searchResult.manga.isNotEmpty()) {
                        _uiState.value = SearchUiState.Success(searchResult)
                    } else {
                        _uiState.value = SearchUiState.Empty
                    }
                }
                .onFailure { exception ->
                    _uiState.value = SearchUiState.Error(exception.message ?: "Terjadi kesalahan")
                }
        }
    }
}