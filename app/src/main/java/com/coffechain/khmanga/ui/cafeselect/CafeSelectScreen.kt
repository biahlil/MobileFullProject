package com.coffechain.khmanga.ui.cafeselect

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.coffechain.khmanga.ui.components.NearbyCafesSection
import com.coffechain.khmanga.ui.components.PopularCafesSection

@Composable
fun CafeSelectScreen(
    uiState: CafeSelectUiState,
    onRefresh: () -> Unit,
    onItemClick: (String) -> Unit,
    onBookmarkClick: (String) -> Unit
) {
    when (uiState) {
        is CafeSelectUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is CafeSelectUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Error: ${uiState.message}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = onRefresh) {
                        Text("Coba Lagi")
                    }
                }
            }
        }
        is CafeSelectUiState.Success -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    PopularCafesSection(cafes = uiState.popularCafes, onItemClick = onItemClick, onBookmarkClick = onBookmarkClick)
                }
                item {
                    NearbyCafesSection(cafes = uiState.nearbyCafes, onItemClick = onItemClick, onBookmarkClick = onBookmarkClick)
                }
            }
        }
    }
}