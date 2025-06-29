package com.coffechain.khmanga.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TextField(
                        value = searchQuery,
                        onValueChange = viewModel::onSearchQueryChanged,
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Cari Kafe atau Manga...") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { viewModel.onSearchQueryChanged("") }) {
                                    Icon(Icons.Default.Clear, contentDescription = "Hapus")
                                }
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        )
                    )
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            Timber.tag("DebugCafeDao").d("UI State: $uiState")
            when (val state = uiState) {
                is SearchUiState.Idle -> {
                    Timber.tag("DebugCafeDao").d("State: $uiState")
                    Text("Mulai mencari...", modifier = Modifier.align(Alignment.Center))
                }
                is SearchUiState.Success -> {
                    Timber.tag("DebugCafeDao").d("State: $uiState")
                    LazyColumn(
                        contentPadding = PaddingValues(vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        if (state.results.cafes.isNotEmpty()) {
                            item {
                                Text("Cafe (${state.results.cafes.size})", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(horizontal = 16.dp))
                            }
                            items(state.results.cafes) { cafe ->
                                Text(cafe.name, modifier = Modifier.padding(horizontal = 16.dp))
                            }
                        }
                        if (state.results.manga.isNotEmpty()) {
                            item {
                                Spacer(Modifier.height(16.dp))
                                Text("MANGA (${state.results.manga.size})", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(horizontal = 16.dp))
                            }
                            items(state.results.manga) { manga ->
                                Text(manga.title, modifier = Modifier.padding(horizontal = 16.dp))
                            }
                        }
                    }
                }
                is SearchUiState.Loading -> {
                    Timber.tag("DebugCafeDao").d("State: $uiState")
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is SearchUiState.Empty -> {
                    Timber.tag("DebugCafeDao").d("State: $uiState")
                    Text("Tidak ada hasil untuk \"$searchQuery\"", modifier = Modifier.align(Alignment.Center))
                }
                is SearchUiState.Error -> {
                    Timber.tag("DebugCafeDao").d("State: $uiState")
                    Text("Error: ${state.message}", modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}