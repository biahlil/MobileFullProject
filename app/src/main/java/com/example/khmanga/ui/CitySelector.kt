package com.example.khmanga.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import androidx.compose.foundation.lazy.items


@Composable
fun CitySelectionSection() {
    Column {
        Text("Select Your City", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        val citys = listOf(
            CityItem("Jakarta", "https://cdn-icons-png.flaticon.com/512/25/25694.png"),
            CityItem("Bandung", "https://cdn-icons-png.flaticon.com/512/25/25694.png"),
            CityItem("Yogyakarta", "https://cdn-icons-png.flaticon.com/512/25/25694.png"),
            CityItem("Malang", "https://cdn-icons-png.flaticon.com/512/25/25694.png"),
            CityItem("Surabaya", "https://cdn-icons-png.flaticon.com/512/25/25694.png"),
            CityItem("Banjarmasin", "https://cdn-icons-png.flaticon.com/512/25/25694.png"),
            CityItem("Balikpapan", "https://cdn-icons-png.flaticon.com/512/25/25694.png"),
        )

        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(citys) { city ->
                CitySelector(city.label, city.url)
            }
        }
    }
}

@Composable
fun CitySelector(cityName: String, imageUrl: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(end = 16.dp)) {
        AsyncImage(
            model =  imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape) // circle
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )
        Text(cityName, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
    }
}

// Helper City items
data class CityItem(val label: String, val url: String)
