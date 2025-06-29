package com.coffechain.khmanga.di

import android.content.Context
import androidx.room.Room
import com.coffechain.khmanga.data.local.AppDatabase
import com.coffechain.khmanga.data.local.dao.CafeDao
import com.coffechain.khmanga.data.local.dao.MangaDao
import com.coffechain.khmanga.data.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "kohimanga_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCafeDao(database: AppDatabase): CafeDao {
        return database.cafeDao()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideMangaDao(database: AppDatabase): MangaDao {
        return database.mangaDao()
    }
}