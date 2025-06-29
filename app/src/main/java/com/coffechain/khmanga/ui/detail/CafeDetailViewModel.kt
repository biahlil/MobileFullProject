package com.coffechain.khmanga.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coffechain.khmanga.domain.repo.CafeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CafeDetailViewModel @Inject constructor(
    private val cafeRepository: CafeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<CafeDetailUiState>(CafeDetailUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        savedStateHandle.get<String>("cafeId")?.let { cafeId ->
            if (cafeId.isNotBlank()) {
                fetchCafeDetails(cafeId)
            } else {
                _uiState.value = CafeDetailUiState.Error("ID Kafe tidak valid.")
            }
        }
    }

    private fun fetchCafeDetails(cafeId: String) {
        viewModelScope.launch {
            _uiState.value = CafeDetailUiState.Loading

            try {
                coroutineScope {
                    val cafeDetailsDeferred = async { cafeRepository.getCafeDetails(cafeId) }
                    val mangaListDeferred = async { cafeRepository.getMangaCollection(cafeId) }
                    val foodMenuDeferred = async { cafeRepository.getFoodMenu(cafeId) }

                    val cafeResult = cafeDetailsDeferred.await()
                    val mangaResult = mangaListDeferred.await()
                    val foodResult = foodMenuDeferred.await()

                    val cafe = cafeResult.getOrThrow()
                    val manga = mangaResult.getOrThrow()
                    val food = foodResult.getOrThrow()

                    _uiState.value = CafeDetailUiState.Success(
                        cafe = cafe,
                        mangaList = manga,
                        foodMenu = food
                    )
                }
            } catch (e: Exception) {
                _uiState.value = CafeDetailUiState.Error(e.message ?: "Gagal memuat data")
            }
        }
    }
}