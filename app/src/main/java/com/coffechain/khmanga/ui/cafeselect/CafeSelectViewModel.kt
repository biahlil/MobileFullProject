package com.coffechain.khmanga.ui.cafeselect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coffechain.khmanga.domain.repo.CafeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import timber.log.Timber

@HiltViewModel
class CafeSelectViewModel @Inject constructor(
    private val cafeRepository: CafeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CafeSelectUiState>(CafeSelectUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        fetchCafeData()
    }

    fun fetchCafeData() {
        _uiState.update { CafeSelectUiState.Loading }

        viewModelScope.launch {
            val result = cafeRepository.getCafes()

            result.fold(
                onSuccess = { allCafes ->

                    Timber.tag("CafeSelectViewModel").d("Berhasil mengambil ${allCafes.size} kafe.")
                    _uiState.update {
                        CafeSelectUiState.Success(
                            popularCafes = allCafes,
                            nearbyCafes = allCafes.shuffled() // Contoh acak untuk "nearby"
                        )
                    }
                },
                onFailure = { exception ->
                    Timber.tag("CafeSelectViewModel").e(exception, "Gagal mengambil data kafe.")
                    _uiState.update {
                        CafeSelectUiState.Error(exception.message ?: "Terjadi kesalahan tidak diketahui")
                    }
                }
            )
        }
    }
}