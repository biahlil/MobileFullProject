package com.coffechain.khmanga.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.coffechain.khmanga.data.local.entity.MangaEntity

@Dao
interface MangaDao {
    @Query("SELECT * FROM mangas WHERE name LIKE '%' || :query || '%'")
    suspend fun searchMangaByTitle(query: String): List<MangaEntity>
}