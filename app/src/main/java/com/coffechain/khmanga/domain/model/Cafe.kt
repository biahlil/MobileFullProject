package com.coffechain.khmanga.domain.model

data class Cafe(
    val id: String,
    val imageUrl: String?,
    val name: String,
    val description: String,
    val address: String,
    val averageRating: Double,
    val amenities: List<String>,
    val location: String,
    val booths: List<Booth>
)
