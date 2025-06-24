package com.coffechain.khmanga.domain.model

data class Food(
    val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val category: String, // Contoh: "Makanan", "Minuman", "Cemilan"
    val size: String? = null // Opsional, jadi bisa null jika tidak ada ukuran
)
