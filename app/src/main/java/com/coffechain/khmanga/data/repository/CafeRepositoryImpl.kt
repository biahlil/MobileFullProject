@file:Suppress("UNCHECKED_CAST", "UNCHECKED_CAST")

package com.coffechain.khmanga.data.repository

import android.annotation.SuppressLint
import com.coffechain.khmanga.domain.model.Booth
import com.coffechain.khmanga.domain.model.Cafe
import com.coffechain.khmanga.domain.model.Food
import com.coffechain.khmanga.domain.model.Manga
import com.coffechain.khmanga.domain.model.Review
import com.coffechain.khmanga.domain.repo.CafeRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.String

@Singleton
class CafeRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : CafeRepository {

    @SuppressLint("BinaryOperationInTimber")
    override suspend fun getCafes(): Result<List<Cafe>> {
        return try {
            val snapshot = db.collection("cafes").get().await()
            val cafes = snapshot.documents.mapNotNull { doc ->
                // Proses mapping dari data Firestore ke domain model
                val boothsList = (doc.get("booths") as? List<Map<String, Any>>)?.map { boothMap ->
                    Booth(
                        id = boothMap["boothId"] as? String ?: "",
                        name = boothMap["name"] as? String ?: "",
                        price = boothMap["price"] as? Double ?: 0.0,
                        capacity = (boothMap["capacity"] as? Long)?.toInt() ?: 0,
                        type = boothMap["type"] as? String ?: ""
                    )
                } ?: emptyList()

                Cafe(
                    id = doc.id,
                    name = doc.getString("name") ?: "",
                    description = doc.getString("description") ?: "",
                    address = doc.getString("address") ?: "",
                    location = doc.getString("location") ?: "",
                    averageRating = doc.getDouble("averageRating") ?: 0.0,
                    amenities = doc.get("amenities") as? List<String> ?: emptyList(),
                    booths = boothsList
                )
            }
            Timber.tag("CafeRepositoryImpl").d("$cafes, Jumlah Cafe: ${cafes.size}")
            Result.success(cafes)
        } catch (e: Exception) {
            Timber.tag("CafeRepositoryImpl").e(e, "Failed to get cafes")
            Result.failure(e)
        }
    }


    override suspend fun getMangaCollection(cafeId: String): Result<List<Manga>> {
        return try {
            val snapshot = db.collection("cafes").document(cafeId).collection("mangaCollection").get().await()

            val mangaList = snapshot.documents.mapNotNull { doc ->
                Manga(
                    id = doc.id, // Ambil id dari dokumen itu sendiri
                    title = doc.getString("title") ?: "",
                    genres = doc.get("genres") as? List<String> ?: emptyList(),
                    availableVolumes = (doc.get("availableVolumes") as? List<Int>)?.map {
                        it.toInt()
                    } ?: emptyList()
                )
            }
            Timber.tag("CafeRepositoryImpl").d("$mangaList, Jumlah Manga: ${mangaList.size}")
            Result.success(mangaList)
        } catch (e: Exception) {
            Timber.tag("CafeRepositoryImpl").e(e, "Failed to get manga collection for cafe id: $cafeId")
            Result.failure(e)
        }
    }

    override suspend fun getFoodMenu(cafeId: String): Result<List<Food>> {
        return try {
            val snapshot = db.collection("cafes").document(cafeId).collection("foodMenu").get().await()
            val foodList = snapshot.documents.mapNotNull { food ->
                Food(
                    id = food.id, // Ambil id dari dokumen itu sendiri
                    title = food.getString("title") ?: "",
                    description = food.get("description") as? String ?: "",
                    price = food.get("price") as? Double ?: 0.0,
                    category = food.get("category") as? String ?: "",
                    size = food.get("size") as? String ?: ""
                )
            }
            Timber.tag("CafeRepositoryImpl").d("$foodList, Jumlah Food: ${foodList.size}")
            Result.success(foodList)
        } catch (e: Exception) {
            Timber.tag("CafeRepositoryImpl").e(e, "Failed to get food menu for cafe id: $cafeId")
            Result.failure(e)
        }
    }

    override suspend fun getReviews(cafeId: String): Result<List<Review>> {
        return try {
            val snapshot = db.collection("cafes").document(cafeId).collection("reviews").get().await()
            val reviewList = snapshot.documents.mapNotNull { review ->
                Review(
                    id = review.id, // Ambil id dari dokumen itu sendiri
                    userId = review.get("userId") as? String ?: "",
                    userName = review.get("username") as? String ?: "",
                    rating = review.get("rating") as? Double ?: 0.0,
                    comment = review.get("comment") as? String ?: "",
                    createdAt = review.getTimestamp("createdAt")?.toDate() ?: java.util.Date(0)
                )
            }
            Timber.tag("CafeRepositoryImpl").d("$reviewList, Jumlah Review: ${reviewList.size}")
            Result.success(reviewList)
        } catch (e: Exception) {
            Timber.tag("CafeRepositoryImpl").e(e, "Failed to get reviews for cafe id: $cafeId")
            Result.failure(e)
        }
    }
}