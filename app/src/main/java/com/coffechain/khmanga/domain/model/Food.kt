package com.coffechain.khmanga.domain.model

data class Food(
    val id: String,
    val imageUrl: String?,
    val title: String,
    val description: String,
    val price: Double,
    val category: String,
    val size: String? = null
)
