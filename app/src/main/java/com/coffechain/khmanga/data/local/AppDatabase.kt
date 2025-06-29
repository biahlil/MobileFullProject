package com.coffechain.khmanga.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.coffechain.khmanga.data.local.dao.CafeDao
import com.coffechain.khmanga.data.local.dao.MangaDao
import com.coffechain.khmanga.data.local.dao.UserDao
import com.coffechain.khmanga.data.local.entity.CafeEntity
import com.coffechain.khmanga.data.local.entity.FoodEntity
import com.coffechain.khmanga.data.local.entity.MangaEntity
import com.coffechain.khmanga.data.local.entity.UserEntity

@Database(
    entities = [CafeEntity::class, UserEntity::class, MangaEntity::class, FoodEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cafeDao(): CafeDao
    abstract fun userDao(): UserDao
    abstract fun mangaDao(): MangaDao
}