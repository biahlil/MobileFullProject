package com.coffechain.khmanga.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.coffechain.khmanga.domain.repo.CafeRepository
import com.coffechain.khmanga.domain.repo.MangaRepository
import com.coffechain.khmanga.domain.repo.UserRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val cafeRepository: CafeRepository,
    private val userRepository: UserRepository,
    private val mangaRepository: MangaRepository
) : CoroutineWorker(appContext, workerParams) {


    override suspend fun doWork(): Result {
        return try {
            Timber.tag("SyncWorker").d("SyncWorker: Memulai pekerjaan sinkronisasi latar belakang...")

            cafeRepository.clearLocalCafes()
            mangaRepository.clearLocalManga()

            // Panggil semua fungsi sinkronisasi dari repository Anda
            cafeRepository.syncCafesFromFirestore()
            mangaRepository.syncAllMangaFromFirestore()
            userRepository.syncUserProfile()
            // Panggil sync untuk manga, food, dll. di sini

            Timber.tag("SyncWorker").d("SyncWorker: Pekerjaan sinkronisasi selesai dengan sukses.")
            Result.success()
        } catch (e: Exception) {
            Timber.tag("SyncWorker").e(e, "SyncWorker: Pekerjaan sinkronisasi gagal.")
            Result.failure()
        }
    }
}