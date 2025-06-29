package com.coffechain.khmanga.data.local.dao

import androidx.room.*
import com.coffechain.khmanga.data.local.entity.CafeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CafeDao {
    @Query("SELECT * FROM cafes")
    fun observeAllCafes(): Flow<List<CafeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cafes: List<CafeEntity>)

    @Query("DELETE FROM cafes")
    suspend fun clearAll()

    @Query("SELECT * FROM cafes WHERE name LIKE '%' || :query || '%'")
    suspend fun searchCafesByName(query: String): List<CafeEntity>
}