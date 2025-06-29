package com.coffechain.khmanga.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.coffechain.khmanga.domain.model.Booth

@Entity(tableName = "cafes")
data class CafeEntity(
    @PrimaryKey val id: String,
    val imageUrl: String,
    val name: String,
    val description: String,
    val address: String,
    val averageRating: Double,
    val amenities: List<String>,
    val booths: List<Booth>
)