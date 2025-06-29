package com.coffechain.khmanga.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mangas")
data class MangaEntity(
    @PrimaryKey val id: String,
    val name: String,
    val genres: List<String>,
    val availableVolumes: List<Int>
)
