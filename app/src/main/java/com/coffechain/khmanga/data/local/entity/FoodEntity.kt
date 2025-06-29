package com.coffechain.khmanga.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "foods")
data class FoodEntity(
    @PrimaryKey val id: String,
    val name: String? = null,
    val description: String? = null,
    val price: Double? = null,
    val category: String? = null,
    val size: String? = null
)