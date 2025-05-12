package com.example.khmanga.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.khmanga.ui.theme.KōhīMangaTheme

@Composable
fun CafeSelectScreen() {
    Scaffold(
        topBar = { CafeTopBar(
            titleText = "Jakarta",
            onNavigationClick = { /*TODO(nav back)*/ }
        ) },
        bottomBar = { CafeBottomNavBar(
            selectedIndex = 1,
            onItemSelected = { /*TODO(switch tab)*/ },
        ) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            Spacer(Modifier.height(8.dp))
            CitySelectionSection()
            Spacer(modifier = Modifier.height(24.dp))
            PopularCafesSection()
            Spacer(modifier = Modifier.height(24.dp))
            NearbyCafesSection()
        }
    }
}

@Preview
@Composable
private fun CafeSelectScreenPrev() {
    KōhīMangaTheme{
        CafeSelectScreen()
    }
}