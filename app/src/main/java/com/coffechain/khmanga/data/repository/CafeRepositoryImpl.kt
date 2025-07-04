@file:Suppress("UNCHECKED_CAST", "UNCHECKED_CAST")

package com.coffechain.khmanga.data.repository

import android.annotation.SuppressLint
import com.coffechain.khmanga.data.local.SampleData
import com.coffechain.khmanga.data.local.dao.CafeDao
import com.coffechain.khmanga.data.local.toDomainModel
import com.coffechain.khmanga.data.local.toEntity
import com.coffechain.khmanga.data.network.CafeDto
import com.coffechain.khmanga.domain.model.Booth
import com.coffechain.khmanga.domain.model.Cafe
import com.coffechain.khmanga.domain.model.Food
import com.coffechain.khmanga.domain.model.Manga
import com.coffechain.khmanga.domain.model.Review
import com.coffechain.khmanga.domain.repo.CafeRepository
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.String

@Singleton
class CafeRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val cafeDao: CafeDao
) : CafeRepository {

    override fun observeAllCafes(): Flow<List<Cafe>> {
        // Ambil data dari DAO, dan map dari Entity ke Model Domain
        return cafeDao.observeAllCafes().map { cafeEntities ->
            cafeEntities.map { it.toDomainModel() }
        }
    }

    override suspend fun getCafeDetails(cafeId: String): Result<Cafe> {
        return try {
            cafeDao.getCafeById(cafeId)?.let { cafeEntity ->
                Result.success(cafeEntity.toDomainModel())
                } ?: Result.failure(Exception("Cafe not found"))
        } catch (e: Exception) {
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
                    imageUrl = doc.getString("imageUrl") ?: "",
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
                    id = food.id,
                    title = food.getString("title") ?: "",
                    description = food.get("description") as? String ?: "",
                    price = food.get("price") as? Double ?: 0.0,
                    imageUrl = food.get("imageUrl") as? String ?: "",
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
                Timber.tag("CafeRepositoryImpl").d("Memulai proses seeding data lengkap dari SampleData...")
                val batch = db.batch()

                for (cafeData in cafesToSeed) {
                    val cafeRef = db.collection("cafes").document()

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
                Timber.tag("CafeRepositoryImpl").i("Seeding data lengkap berhasil.")
                Result.success(Unit)

            } catch (e: Exception) {
                Timber.tag("CafeRepositoryImpl").e(e, "Gagal melakukan seeding data lengkap.")
                Result.failure(e)
            }

    }

    override suspend fun syncCafesFromFirestore(): Result<Unit> {
        return try {
            Timber.tag("DebugCafeDao").d("Memulai sinkronisasi data dari Firestore ke Room...")
            val snapshot = db.collection("cafes").get().await()
            val cafeEntities = snapshot.documents.mapNotNull { doc ->
                val dto = doc.toObject(CafeDto::class.java)
                dto?.toEntity(id = doc.id)
            }
            Timber.tag("DebugCafeDao").i("$cafeEntities cafe berhasil disinkronkan ke Room.")

            if (cafeEntities.isNotEmpty()) {
                cafeDao.insertAll(cafeEntities)
                Timber.tag("DebugCafeDao").i("$cafeEntities cafe berhasil disinkronkan ke Room.")
            } else {
                Timber.tag("DebugCafeDao").w("Tidak ada data kafe di Firestore untuk disinkronkan.")
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "Sinkronisasi gagal.")
            Result.failure(e)
        }
    }

    override suspend fun clearLocalCafes() {
        if (cafeDao.getCafeCount() > 0) {
            cafeDao.clearAll()
            Timber.i("Tabel 'cafes' tidak kosong, cache lokal berhasil dibersihkan.")
        } else {
            Timber.i("Tabel 'cafes' sudah kosong, tidak ada aksi pembersihan.")
        }
    }
}