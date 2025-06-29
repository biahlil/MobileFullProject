package com.coffechain.khmanga.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coffechain.khmanga.domain.model.Cafe
import com.coffechain.khmanga.ui.theme.KōhīMangaTheme


@Composable
fun PopularCafesSection(
    cafes: List<Cafe>
) {
    Column {
        Text(
            "Populer Sekarang",
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
            items(items = cafes, key = { it.id }) { cafe ->
                CafeItemVar2(
                    cafe = cafe,
                    onItemClick = { /* TODO: Navigasi ke detail cafe */ },
                    onBookmarkClick = { /* TODO: Logika bookmark */ }
                )
            }
        }
    }
}

@Preview
@Composable
private fun PopularCafesSectionPreview() {
    val cafe1 = Cafe(
        id = "1",
        imageUrl = null,
        name = "MangaCafe1",
        description = "Suporting Indonesia lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum ",
        address = "Jl. Ahmad Yani no.45 - Map",
        averageRating = 4.83,
        amenities = listOf("kursi", "meja", "toilet"),
        location = "jakarta",
        booths = listOf()
    )
    val cafe2 = Cafe(
        id = "1",
        imageUrl = null,
        name = "MangaCafe1",
        description = "Suporting Indonesia lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum ",
        address = "Jl. Ahmad Yani no.45 - Map",
        averageRating = 4.83,
        amenities = listOf("kursi", "meja", "toilet"),
        location = "jakarta",
        booths = listOf()
    )
    val cafe3 = Cafe(
        id = "1",
        imageUrl = null,
        name = "MangaCafe1",
        description = "Suporting Indonesia lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum ",
        address = "Jl. Ahmad Yani no.45 - Map",
        averageRating = 4.83,
        amenities = listOf("kursi", "meja", "toilet"),
        location = "jakarta",
        booths = listOf()
    )

    val cafes = listOf(cafe1, cafe2, cafe3)

    KōhīMangaTheme {
        PopularCafesSection(
            cafes = cafes,
        )
    }
}