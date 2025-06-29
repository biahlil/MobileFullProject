package com.coffechain.khmanga.domain.repo

import com.coffechain.khmanga.domain.model.Cafe
import com.coffechain.khmanga.domain.model.Food
import com.coffechain.khmanga.domain.model.Manga
import com.coffechain.khmanga.domain.model.Review
import kotlinx.coroutines.flow.Flow


interface CafeRepository {
    suspend fun getCafes(): Result<List<Cafe>>
    suspend fun getMangaCollection(cafeId: String): Result<List<Manga>>
    suspend fun getFoodMenu(cafeId: String): Result<List<Food>>
    suspend fun getReviews(cafeId: String): Result<List<Review>>
    suspend fun seedDatabase(): Result<Unit>
    suspend fun syncCafesFromFirestore(): Result<Unit>
    fun observeAllCafes(): Flow<List<Cafe>>
    suspend fun getCafeDetails(cafeId: String): Result<Cafe>
    suspend fun clearLocalCafes()
}