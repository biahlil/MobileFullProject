package com.coffechain.khmanga.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coffechain.khmanga.R
import com.coffechain.khmanga.ui.theme.KōhīMangaTheme


@Composable
fun NearbyCafesSection() {
    Column {
        Text(
            "Di Sekitar Anda",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 8.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),

        ) {
            items(4) {
                CafeItem(
                    name = "Mangacafe2",
                    imageUrl = R.drawable.jakarta,
                    address = "Jl. Surabaya no.21 - Map",
                    description = "Supporting line text lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum ",
                    ratingStars = 5,
                    bookmarkButtonClick = {}
                )
            }

        }
    }
}

@Preview
@Composable
private fun CafeItemPreview() {
    KōhīMangaTheme {
        CafeItem(
            name = "Jafar Coffe",
            imageUrl = R.drawable.jakarta,
            address = "Jl. Surabaya no.21 - Map",
            description = "Kopi Terbaik",
            ratingStars = 5,
            bookmarkButtonClick = {}
        )
    }
}

@Preview
@Composable
private fun NearbyCafesSectionPreview() {
    KōhīMangaTheme {
        NearbyCafesSection()
    }
}
