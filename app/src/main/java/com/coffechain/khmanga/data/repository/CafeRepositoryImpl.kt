@file:Suppress("UNCHECKED_CAST", "UNCHECKED_CAST")

package com.coffechain.khmanga.data.repository

import android.annotation.SuppressLint
import com.coffechain.khmanga.data.local.SampleData
import com.coffechain.khmanga.domain.model.Booth
import com.coffechain.khmanga.domain.model.Cafe
import com.coffechain.khmanga.domain.model.Food
import com.coffechain.khmanga.domain.model.Manga
import com.coffechain.khmanga.domain.model.Review
import com.coffechain.khmanga.domain.repo.CafeRepository
import com.google.firebase.firestore.FieldValue
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
                    imageUrl = doc.getString("imageUrl") ?: "",
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
                    id = doc.id,
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

    override suspend fun seedDatabase(): Result<Unit> {
            return try {
                val cafesToSeed = SampleData.sampleCafes
                val mangaToSeed = SampleData.sampleManga
                val foodsToSeed = SampleData.sampleFoods
                val reviewsToSeed = SampleData.sampleReviews
                Timber.d("Memulai proses seeding data lengkap dari SampleData...")
                val batch = db.batch()

                for (cafeData in cafesToSeed) {
                    val cafeRef = db.collection("cafes").document()

                    // --- PERBAIKAN LOGIKA ADA DI SINI ---
                    // Ubah List<Booth> menjadi List<Map<String, Any>> sebelum disimpan
                    val boothsForFirestore = cafeData.booths.map { booth ->
                        mapOf(
                            "boothId" to booth.id,
                            "name" to booth.name,
                            "price" to booth.price,
                            "capacity" to booth.capacity,
                            "type" to booth.type
                        )
                    }

                    val cafeMap = hashMapOf(
                        "name" to cafeData.name,
                        "description" to cafeData.description,
                        "address" to cafeData.address,
                        "location" to cafeData.location,
                        "imageUrl" to cafeData.imageUrl,
                        "amenities" to cafeData.amenities,
                        "booths" to boothsForFirestore, // <-- Simpan map yang sudah dikonversi
                        "averageRating" to SampleData.sampleReviews.map { it["rating"] as Double }.average()
                    )
                    batch.set(cafeRef, cafeMap)

//                    Tambahkan sub-koleksi manga ke batch
                    mangaToSeed.shuffled().take(3).forEach { manga -> // Ambil 3 manga acak
                        val mangaRef = cafeRef.collection("mangaCollection").document()
                        batch.set(mangaRef, manga)
                    }

                    // Tambahkan sub-koleksi makanan ke batch
                    foodsToSeed.shuffled().take(3).forEach { food -> // Ambil 3 makanan acak
                        val foodRef = cafeRef.collection("foodMenu").document()
                        batch.set(foodRef, food)
                    }

                    // Tambahkan sub-koleksi review ke batch
                    reviewsToSeed.shuffled().take(2).forEach { review -> // Ambil 2 review acak
                        val reviewRef = cafeRef.collection("reviews").document()
                        // Tambahkan timestamp server saat seeding
                        val reviewWithTimestamp = review.plus("timestamp" to FieldValue.serverTimestamp())
                        batch.set(reviewRef, reviewWithTimestamp)
                    }
                }

                batch.commit().await()
                Timber.i("Seeding data lengkap berhasil.")
                Result.success(Unit)

            } catch (e: Exception) {
                Timber.e(e, "Gagal melakukan seeding data lengkap.")
                Result.failure(e)
            }

    }
}