package com.coffechain.khmanga.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coffechain.khmanga.data.local.entity.MangaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MangaDao {
    @Query("SELECT * FROM mangas WHERE LOWER(name) LIKE '%' || :query || '%'")
    suspend fun searchMangaByTitle(query: String): List<MangaEntity>

    @Query("DELETE FROM mangas")
    suspend fun clearAll()

    @Query("SELECT * FROM mangas WHERE cafeId = :cafeId")
    fun observeMangaForCafe(cafeId: String): Flow<List<MangaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(manga: List<MangaEntity>)

    @Query("SELECT COUNT(*) FROM mangas")
    suspend fun getMangaCount(): Int

}
