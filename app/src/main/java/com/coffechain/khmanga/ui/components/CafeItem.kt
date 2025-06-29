package com.coffechain.khmanga.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.coffechain.khmanga.ui.theme.KōhīMangaTheme
import com.coffechain.khmanga.R
import com.coffechain.khmanga.domain.model.Cafe


@Composable
fun CafeItem(
    modifier: Modifier = Modifier,
    cafe: Cafe,
    onItemClick: (String) -> Unit,
    onBookmarkClick: () -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = modifier
            .width(350.dp)
            .height(250.dp)
            .clickable(onClick = { onItemClick(cafe.id) })
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
        ) {
            AsyncImage(
                model  = cafe.imageUrl ?: R.drawable.jakarta,
                contentDescription = cafe.name,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp))
            )
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = cafe.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))
                Column{
                    Text(
                        text = cafe.address,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                        )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = cafe.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 6
                        )
                }
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
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = onBookmarkClick) {
                        Icon(Icons.Default.FavoriteBorder, contentDescription = null)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CafeItemPreview() {
    val cafe = Cafe(
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
    KōhīMangaTheme {
        CafeItem(
            cafe = cafe,
            onItemClick = { },
            onBookmarkClick = { },
        )
    }
}
