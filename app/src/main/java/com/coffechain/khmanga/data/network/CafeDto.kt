package com.coffechain.khmanga.data.network

data class CafeDto(
    val name: String? = null,
    val description: String? = null,
    val address: String? = null,
    val location: String? = null,
    val imageUrl: String? = null,
    val averageRating: Double? = null,
    val amenities: List<String>? = null,
    val booths: List<Map<String, Any>>? = null
)