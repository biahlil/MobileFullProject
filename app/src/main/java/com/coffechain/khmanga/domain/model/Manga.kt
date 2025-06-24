package com.coffechain.khmanga.domain.model

data class Manga(
    val id: String,
    val title: String,
    val genres: List<String>,
    val availableVolumes: List<Int>
)
