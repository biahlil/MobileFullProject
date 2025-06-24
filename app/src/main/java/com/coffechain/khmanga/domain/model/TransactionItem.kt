package com.coffechain.khmanga.domain.model

// Merepresentasikan satu item di dalam keranjang transaksi
data class TransactionItem(
    val itemId: String,
    val itemName: String,
    val itemType: String,
    val quantity: Int,
    val pricePerItem: Double
)