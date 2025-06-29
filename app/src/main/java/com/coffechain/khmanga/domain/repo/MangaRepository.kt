package com.coffechain.khmanga.domain.repo

import com.coffechain.khmanga.domain.model.Manga
import kotlinx.coroutines.flow.Flow

interface MangaRepository {
    fun observeMangaForCafe(cafeId: String): Flow<List<Manga>>
    suspend fun clearLocalManga()
    suspend fun syncAllMangaFromFirestore(): Result<Unit>
}