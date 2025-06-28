package com.coffechain.khmanga.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coffechain.khmanga.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    /**
     * Mengamati satu profil pengguna berdasarkan UID-nya.
     * Menggunakan Flow agar UI bisa update secara reaktif.
     */
    @Query("SELECT * FROM users WHERE uid = :uid LIMIT 1")
    fun observeUserById(uid: String): Flow<UserEntity?>

    /**
     * Memasukkan atau memperbarui satu pengguna.
     * Jika pengguna dengan UID yang sama sudah ada, datanya akan diganti.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateUser(user: UserEntity)

    /**
     * Menghapus semua data dari tabel pengguna.
     * Berguna saat proses logout.
     */
    @Query("DELETE FROM users")
    suspend fun clearAll()
}