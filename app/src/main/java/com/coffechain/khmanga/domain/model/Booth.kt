package com.coffechain.khmanga.domain.model

data class Booth(
    val id: String,
    val name: String,
    val price: Double,
    val capacity: Int,
    val type: String // Contoh: "Sofa", "Kursi Tunggal", "Lesehan"
)
