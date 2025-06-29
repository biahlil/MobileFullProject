package com.coffechain.khmanga

import android.app.Application
import com.cloudinary.android.MediaManager
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import androidx.work.*
import com.coffechain.khmanga.data.worker.SyncWorker
import androidx.hilt.work.HiltWorkerFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltAndroidApp
class KohiMangaApplication : Application(),Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .build()


    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.tag("KohiMangaApplication").i("Inisiasi Timber berhasil")
        }
        initCloudinary()
        setupBackgroundSync()
    }

    private fun setupBackgroundSync() {
        val workManager = WorkManager.getInstance(this)

        val initialSyncRequest = OneTimeWorkRequestBuilder<SyncWorker>().build()
        workManager.enqueueUniqueWork(
            "InitialFirestoreSync",
            ExistingWorkPolicy.REPLACE,
            initialSyncRequest
        )
        Timber.tag("DebugCafeDao").i("Sinkronisasi berhasil diinisialisasi.")

        // Request untuk sinkronisasi berkala
        val periodicSyncRequest = PeriodicWorkRequestBuilder<SyncWorker>(4, TimeUnit.HOURS)
            .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
            .build()
        workManager.enqueueUniquePeriodicWork(
            "PeriodicFirestoreSync",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicSyncRequest
        )
        Timber.tag("DebugCafeDao").i("Pekerjaan sinkronisasi latar belakang berhasil dijadwalkan.")
    }

    private fun initCloudinary() {
        val config = mapOf(
            "cloud_name" to "ddim30d7v", // Ganti dengan Cloud Name Anda
            "api_key" to "259818154127694",       // Ganti dengan API Key Anda
            "api_secret" to "EPX5jL6x80794UT2JV-QBORlOB8", // Ganti dengan API Secret Anda
            "secure" to true
        )
        MediaManager.init(this, config)
        Timber.i("Cloudinary MediaManager berhasil diinisialisasi.")
    }

}
