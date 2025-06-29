package com.coffechain.khmanga.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.coffechain.khmanga.domain.model.Cafe
import com.coffechain.khmanga.domain.model.Food
import com.coffechain.khmanga.domain.model.Manga
import com.coffechain.khmanga.ui.components.BoothListItem
import com.coffechain.khmanga.ui.components.FoodListItem
import com.coffechain.khmanga.ui.components.MangaListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CafeDetailScreen(
    viewModel: CafeDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

        Box(modifier = Modifier
            .fillMaxSize()
            .padding())
        {
            when (val state = uiState) {
                is CafeDetailUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is CafeDetailUiState.Error -> {
                    Text(text = state.message, modifier = Modifier.align(Alignment.Center))
                }
                is CafeDetailUiState.Success -> {
                    CafeDetailContent(
                        cafe = state.cafe,
                        mangaList = state.mangaList,
                        foodMenu = state.foodMenu
                    )
                }
            }
        }
    }

@Composable
private fun CafeDetailContent(
    cafe: Cafe,
    mangaList: List<Manga>,
    foodMenu: List<Food>
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Booth", "Koleksi", "Makanan")

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Column {
                AsyncImage(
                    model = cafe.imageUrl,
                    contentDescription = cafe.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth().height(250.dp)
                )
                Column(Modifier.padding(16.dp)) {
                    Text(text = cafe.name, style = MaterialTheme.typography.headlineMedium)
                    Spacer(Modifier.height(4.dp))
                    Text(text = cafe.address, style = MaterialTheme.typography.bodyMedium)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        val fullStars = cafe.averageRating.toInt()
                        val halfStar = cafe.averageRating - fullStars >= 0.5
                        val emptyStars = 5 - fullStars - if (halfStar) 1 else 0

                        repeat(fullStars) {
                            Icon(
                                Icons.Filled.Star,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        if (halfStar) {
                            Icon(
                                Icons.AutoMirrored.Filled.StarHalf,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        repeat(emptyStars) {
                            Icon(
                                Icons.Outlined.StarOutline,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "%.1f".format(cafe.averageRating),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        // Tab Row
        stickyHeader {
            TabRow(selectedTabIndex = selectedTabIndex, modifier = Modifier.fillMaxWidth()) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(text = title) }
                    )
                }
            }
        }

        when (selectedTabIndex) {
            0 -> items(cafe.booths.size) { index ->
                val booth = cafe.booths[index]
                BoothListItem(booth = booth, onBookClick = {})
            }
            1 -> items(mangaList.size) { index ->
                val manga = mangaList[index]
                MangaListItem(manga = manga)
            }
            2 -> items(foodMenu.size) { index ->
                val food = foodMenu[index]
                FoodListItem(food = food, onAddToCartClick = {})
            }
        }
    }
}