package com.coffechain.khmanga.ui.cafeselect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coffechain.khmanga.domain.repo.CafeRepository
import com.coffechain.khmanga.data.local.dao.CafeDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CafeSelectViewModel @Inject constructor(
    private val cafeRepository: CafeRepository,
    private val cafeDao: CafeDao
) : ViewModel() {

    val uiState: StateFlow<CafeSelectUiState> = cafeRepository.observeAllCafes()
        .map { cafes ->
            if (cafes.isEmpty()) {
                CafeSelectUiState.Loading
            } else {
                CafeSelectUiState.Success(popularCafes = cafes, nearbyCafes = cafes)
            }
        }
        .catch {
                e -> emit(CafeSelectUiState.Error(e.message ?: "Error"))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CafeSelectUiState.Loading
        )
}