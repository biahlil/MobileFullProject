package com.coffechain.khmanga.domain.repo

import com.coffechain.khmanga.domain.model.Cafe
import com.coffechain.khmanga.domain.model.Manga

interface SearchRepository {
    suspend fun searchEverything(query: String): Result<SearchResult>
}

data class SearchResult(
    val cafes: List<Cafe>,
    val manga: List<Manga>
//    val foods: List<Food>
)
