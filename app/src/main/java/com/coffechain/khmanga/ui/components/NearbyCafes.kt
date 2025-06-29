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
import androidx.compose.ui.unit.dp
import com.coffechain.khmanga.domain.model.Cafe


@Composable
fun NearbyCafesSection(
    cafes: List<Cafe>,
    onItemClick: (String) -> Unit,
    onBookmarkClick: (String) -> Unit
) {
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
            items(items = cafes, key = { it.id }) { cafe ->
                CafeItem(
                    cafe = cafe,
                    onItemClick = onItemClick,
                    onBookmarkClick = onBookmarkClick
                )
            }
        }
    }
}
