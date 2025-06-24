package com.coffechain.khmanga.domain.repo

import com.coffechain.khmanga.domain.model.Cafe
import com.coffechain.khmanga.domain.model.Food
import com.coffechain.khmanga.domain.model.Manga
import com.coffechain.khmanga.domain.model.Review


interface CafeRepository {
    suspend fun getCafes(): Result<List<Cafe>>
    suspend fun getMangaCollection(cafeId: String): Result<List<Manga>>
    suspend fun getFoodMenu(cafeId: String): Result<List<Food>>
    suspend fun getReviews(cafeId: String): Result<List<Review>>
}