package com.coffechain.khmanga.domain.repo

import com.coffechain.khmanga.domain.model.Transaction
import com.coffechain.khmanga.domain.model.TransactionItem

interface TransactionRepository {
    /**
     * Membuat transaksi baru di Firestore.
     * return id dari transaksi yang baru dibuat.
     */
    suspend fun createTransaction(
        userId: String,
        userName: String,
        cafeId: String,
        cafeName: String,
        totalAmount: Double,
        items: List<TransactionItem>
    ): Result<String>

//     Mengambil riwayat transaksi untuk seorang pengguna.
    suspend fun getTransactionHistory(userId: String): Result<List<Transaction>>
}