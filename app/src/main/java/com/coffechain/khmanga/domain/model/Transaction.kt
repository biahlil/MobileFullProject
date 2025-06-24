package com.coffechain.khmanga.domain.model
import com.google.firebase.Timestamp
import java.util.Date

// Merepresentasikan satu transaksi utuh
data class Transaction(
    val id: String,
    val userId: String,
    val userName: String,
    val cafeId: String,
    val cafeName: String,
    val transactionDate: Date,
    val totalAmount: Double,
    val status: String,
    val items: List<TransactionItem>
)