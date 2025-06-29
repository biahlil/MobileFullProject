package com.coffechain.khmanga.data.network

data class MangaDto(
    val title: String? = null,
    val imageUrl: String? = null,
    val genres: List<String>? = null,
    val availableVolumes: List<Long>? = null
)