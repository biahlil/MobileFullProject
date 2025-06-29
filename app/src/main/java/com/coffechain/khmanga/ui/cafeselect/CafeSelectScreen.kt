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
    onRefresh: () -> Unit
) {
    when (uiState) {
        is CafeSelectUiState.Loading -> {
            // Tampilkan loading indicator di tengah layar
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is CafeSelectUiState.Error -> {
            // Tampilkan pesan error dan tombol untuk mencoba lagi
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
            // Jika sukses, baru tampilkan konten utama
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
//                item {
//                    CitySelectionSection
//                }
                item {
                    PopularCafesSection(cafes = uiState.popularCafes)
                }
                item {
                    NearbyCafesSection(cafes = uiState.nearbyCafes)
                }
            }
        }
    }
}

//@Preview
//@Composable
//private fun CafeSelectScreenPrev() {
//    val cafe1 = Cafe(
//        id = "1",
//        imageUrl = null,
//        name = "MangaCafe1",
//        description = "Suporting Indonesia lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum ",
//        address = "Jl. Ahmad Yani no.45 - Map",
//        averageRating = 4.83,
//        amenities = listOf("kursi", "meja", "toilet"),
//        location = "jakarta",
//        booths = listOf()
//    )
//    val cafe2 = Cafe(
//        id = "1",
//        imageUrl = null,
//        name = "MangaCafe1",
//        description = "Suporting Indonesia lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum ",
//        address = "Jl. Ahmad Yani no.45 - Map",
//        averageRating = 4.83,
//        amenities = listOf("kursi", "meja", "toilet"),
//        location = "jakarta",
//        booths = listOf()
//    )
//    val cafe3 = Cafe(
//        id = "1",
//        imageUrl = null,
//        name = "MangaCafe1",
//        description = "Suporting Indonesia lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum ",
//        address = "Jl. Ahmad Yani no.45 - Map",
//        averageRating = 4.83,
//        amenities = listOf("kursi", "meja", "toilet"),
//        location = "jakarta",
//        booths = listOf()
//    )
//
//    val cafes = listOf(cafe1, cafe2, cafe3)
//
//    KōhīMangaTheme{
//        CafeSelectScreen(
//    }
//}