package com.coffechain.khmanga.data.repository

import com.coffechain.khmanga.data.local.dao.MangaDao
import com.coffechain.khmanga.data.local.entity.MangaEntity
import com.coffechain.khmanga.data.local.toDomainModel
import com.coffechain.khmanga.data.local.toEntity
import com.coffechain.khmanga.data.network.MangaDto
import com.coffechain.khmanga.domain.model.Manga
import com.coffechain.khmanga.domain.repo.MangaRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MangaRepositoryImpl @Inject constructor(
    private val mangaDao: MangaDao,
    private val db: FirebaseFirestore
) : MangaRepository {

    override fun observeMangaForCafe(cafeId: String): Flow<List<Manga>> {
        return mangaDao.observeMangaForCafe(cafeId).map { mangaEntities ->
            mangaEntities.map { it.toDomainModel() }
        }
    }

    override suspend fun clearLocalManga() {
        // Cek dulu apakah tabelnya ada isinya
        if (mangaDao.getMangaCount() > 0) {
            mangaDao.clearAll()
            Timber.i("Tabel 'manga' tidak kosong, cache lokal berhasil dibersihkan.")
        } else {
            Timber.i("Tabel 'manga' sudah kosong, tidak ada aksi pembersihan.")
        }
    }

    override suspend fun syncAllMangaFromFirestore(): Result<Unit> {
        return try {
            Timber.d("Memulai sinkronisasi semua manga dari Firestore...")

            val cafesSnapshot = db.collection("cafes").get().await()
            val allMangaEntities = mutableListOf<MangaEntity>()

            for (cafeDoc in cafesSnapshot.documents) {
                val cafeId = cafeDoc.id
                val mangaSnapshot = db.collection("cafes").document(cafeId)
                    .collection("mangaCollection").get().await()

                val mangaEntitiesForCafe = mangaSnapshot.documents.mapNotNull { mangaDoc ->
                    val dto = mangaDoc.toObject(MangaDto::class.java)
                    dto?.toEntity(id = mangaDoc.id, cafeId = cafeId)
                }
                allMangaEntities.addAll(mangaEntitiesForCafe)
            }

            if (allMangaEntities.isNotEmpty()) {
                mangaDao.insertAll(allMangaEntities)
                Timber.i("${allMangaEntities.size} manga berhasil disinkronkan ke Room.")
            } else {
                Timber.w("Tidak ada data manga di Firestore untuk disinkronkan.")
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "Sinkronisasi manga gagal.")
            Result.failure(e)
        }
    }
}