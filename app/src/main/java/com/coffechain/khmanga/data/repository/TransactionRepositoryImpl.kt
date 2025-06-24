@file:Suppress("UNCHECKED_CAST")

package com.coffechain.khmanga.data.repository

import com.coffechain.khmanga.domain.model.Transaction
import com.coffechain.khmanga.domain.model.TransactionItem
import com.coffechain.khmanga.domain.repo.TransactionRepository
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : TransactionRepository {

    override suspend fun createTransaction(
        userId: String, userName: String, cafeId: String, cafeName: String,
        totalAmount: Double, items: List<TransactionItem>
    ): Result<String> {
        return try {
            val transactionMap = hashMapOf(
                "userId" to userId,
                "userName" to userName,
                "cafeId" to cafeId,
                "cafeName" to cafeName,
                "transactionDate" to FieldValue.serverTimestamp(), // Gunakan waktu server
                "totalAmount" to totalAmount,
                "status" to "Completed",
                "items" to items.map { item -> // Ubah List<TransactionItem> menjadi List<Map>
                    mapOf(
                        "itemId" to item.itemId,
                        "itemName" to item.itemName,
                        "itemType" to item.itemType,
                        "quantity" to item.quantity,
                        "pricePerItem" to item.pricePerItem
                    )
                }
            )

            val docRef = db.collection("transactions").add(transactionMap).await()
            Timber.tag("TransactionRepoImpl").d("Berhasil membuat transaksi dengan ID: ${docRef.id}")
            Result.success(docRef.id)
        } catch (e: Exception) {
            Timber.tag("TransactionRepoImpl").e(e, "Gagal membuat transaksi")
            Result.failure(e)
        }
    }

    override suspend fun getTransactionHistory(userId: String): Result<List<Transaction>> {
        return try {
            val snapshot = db.collection("transactions")
                .whereEqualTo("userId", userId)
                .orderBy("transactionDate", Query.Direction.DESCENDING)
                .get().await()

            val transactions = snapshot.documents.mapNotNull { doc ->
                val itemsList = (doc.get("items") as? List<Map<String, Any>>)?.map { itemMap ->
                    TransactionItem(
                        itemId = itemMap["itemId"] as? String ?: "",
                        itemName = itemMap["itemName"] as? String ?: "",
                        itemType = itemMap["itemType"] as? String ?: "",
                        quantity = (itemMap["quantity"] as? Long)?.toInt() ?: 0,
                        pricePerItem = itemMap["pricePerItem"] as? Double ?: 0.0
                    )
                } ?: emptyList()

                val timestamp = doc.getTimestamp("transactionDate")
                Transaction(
                    id = doc.id,
                    userId = doc.getString("userId") ?: "",
                    userName = doc.getString("userName") ?: "",
                    cafeId = doc.getString("cafeId") ?: "",
                    cafeName = doc.getString("cafeName") ?: "",
                    transactionDate = timestamp?.toDate() ?: java.util.Date(0), // Konversi Timestamp ke Date
                    totalAmount = doc.getDouble("totalAmount") ?: 0.0,
                    status = doc.getString("status") ?: "",
                    items = itemsList
                )
            }
            Timber.tag("TransactionRepoImpl").d("$transactions, Jumlah Transaksi: ${transactions.size}")
            Result.success(transactions)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}