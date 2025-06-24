package com.coffechain.khmanga.di

import javax.inject.Qualifier

// Qualifier untuk Ktor Client ##
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MangaApiClient

// Qualifier untuk Ktor Client ##
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FireBaseApiClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FirebaseFireStore