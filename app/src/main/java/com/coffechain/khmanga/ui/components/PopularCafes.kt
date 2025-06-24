package com.coffechain.khmanga.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coffechain.khmanga.R


@Composable
fun PopularCafesSection() {
    Column {
        Text("Populer Sekarang", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        CafeItem(
            name = "Mangacafe1",
            imageUrl = R.drawable.jakarta,
            address = "Jl. Ahmad Yani no.45 - Map",
            description = "Supporting line text lorem ipsum...",
            ratingStars = 5,
            bookmarkButtonClick = {}
        )
    }
}
