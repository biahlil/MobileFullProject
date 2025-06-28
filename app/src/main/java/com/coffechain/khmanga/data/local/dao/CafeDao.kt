package com.coffechain.khmanga.data.local.dao

import androidx.room.*
import com.coffechain.khmanga.data.local.entity.CafeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CafeDao {
    /**
     * Mengambil semua kafe dan mengamatinya sebagai Flow.
     * UI akan otomatis update jika ada perubahan di tabel ini.
     */
    @Query("SELECT * FROM cafes")
    fun observeAllCafes(): Flow<List<CafeEntity>>

    /**
     * Memasukkan daftar kafe. Jika ada kafe dengan ID yang sama,
     * data lama akan digantikan dengan data baru.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cafes: List<CafeEntity>)

    /** Menghapus semua data dari tabel kafe. */
    @Query("DELETE FROM cafes")
    suspend fun clearAll()
}