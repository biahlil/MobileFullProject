package com.coffechain.khmanga.data.repository


import com.coffechain.khmanga.data.local.dao.CafeDao
import com.coffechain.khmanga.data.local.dao.MangaDao
import com.coffechain.khmanga.data.local.toDomainModel
import com.coffechain.khmanga.domain.repo.SearchRepository
import com.coffechain.khmanga.domain.repo.SearchResult
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepositoryImpl @Inject constructor(
    private val cafeDao: CafeDao,
    private val mangaDao: MangaDao
) : SearchRepository {

    override suspend fun searchEverything(query: String): Result<SearchResult> {
        return coroutineScope {
            try {
                val cafesDeferred = async { cafeDao.searchCafesByName(query) }
                val mangaDeferred = async { mangaDao.searchMangaByTitle(query) }
                val cafeEntities = cafesDeferred.await()
                val mangaEntities = mangaDeferred.await()
                val cafes = cafeEntities.map { it.toDomainModel() }
                val manga = mangaEntities.map { it.toDomainModel() }
                Timber.tag("SearchRepository").d("Cafes: $cafes")
                Timber.tag("SearchRepository").d("Mangas: $manga")
                Result.success(
                    SearchResult(
                        cafes = cafes,
                        manga = manga
                    ))

            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}

