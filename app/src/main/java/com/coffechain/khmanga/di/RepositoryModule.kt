package com.coffechain.khmanga.di

import com.coffechain.khmanga.data.repository.AuthRepositoryImpl
import com.coffechain.khmanga.data.repository.CafeRepositoryImpl
import com.coffechain.khmanga.data.repository.MangaRepositoryImpl
import com.coffechain.khmanga.data.repository.SearchRepositoryImpl
import com.coffechain.khmanga.data.repository.StorageRepositoryImpl
import com.coffechain.khmanga.data.repository.TransactionRepositoryImpl
import com.coffechain.khmanga.data.repository.UserRepositoryImpl
import com.coffechain.khmanga.domain.repo.AuthRepository
import com.coffechain.khmanga.domain.repo.CafeRepository
import com.coffechain.khmanga.domain.repo.MangaRepository
import com.coffechain.khmanga.domain.repo.SearchRepository
import com.coffechain.khmanga.domain.repo.StorageRepository
import com.coffechain.khmanga.domain.repo.TransactionRepository
import com.coffechain.khmanga.domain.repo.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindMangaRepository(
        mangaRepositoryImpl: MangaRepositoryImpl
    ): MangaRepository



    @Binds
    abstract fun bindCafeRepository(
        cafeRepositoryImpl: CafeRepositoryImpl
    ): CafeRepository

    @Binds
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    abstract fun bindTransactionRepository(
        transactionRepositoryImpl: TransactionRepositoryImpl
    ): TransactionRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindStorageRepository(
        storageRepositoryImpl: StorageRepositoryImpl
    ): StorageRepository

    @Binds
    @Singleton
    abstract fun bindSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ): SearchRepository
}