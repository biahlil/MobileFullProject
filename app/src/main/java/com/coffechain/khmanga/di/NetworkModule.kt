package com.coffechain.khmanga.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import javax.inject.Singleton
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val jsonConfig = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        isLenient = true
    }

    @Provides
    @Singleton
    @MangaApiClient
    fun provideMangaHttpClient(): HttpClient {
        return HttpClient(Android) {
            defaultRequest {
                url("https://api.manga-service.com/v1/")
            }

            install(Logging) {
                level = LogLevel.BODY
            }
            install(ContentNegotiation) {
                json(jsonConfig)
            }
        }
    }

    @Provides
    @Singleton
    @FireBaseApiClient
    fun provideCafeHttpClient(): HttpClient {
        return HttpClient(Android) {
            defaultRequest {
                url("https://api.cafe-service.net/v2/")
            }

            install(Logging) {
                level = LogLevel.HEADERS
            }
            install(ContentNegotiation) {
                json(jsonConfig)
            }
        }
    }

}