package com.coffechain.khmanga.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coffechain.khmanga.R
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.res.painterResource


@Composable
fun CitySelectionSection() {
    Column {
        Text("Select Your City", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        val citys = listOf(
            CityItem("Jakarta", R.drawable.jakarta),
            CityItem("Bandung", R.drawable.bandung),
            CityItem("Yogyakarta", R.drawable.jogja),
            CityItem("Malang", R.drawable.malang),
            CityItem("Surabaya", R.drawable.surabaya),
            CityItem("Banjarmasin", R.drawable.banjarmasin),
            CityItem("Balikpapan", R.drawable.balikpapan),
        )

        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(citys) { city ->
                CitySelector(city.label, city.url)
            }
        }
    }
}

@Composable
fun CitySelector(cityName: String, imageUrl: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(end = 16.dp)) {
        Image(
            painter = painterResource(imageUrl),
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
data class CityItem(val label: String, val url: Int)
