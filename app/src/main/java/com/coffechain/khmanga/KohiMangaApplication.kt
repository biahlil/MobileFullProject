package com.coffechain.khmanga

import android.app.Application
import com.cloudinary.android.MediaManager
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class KohiMangaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Inisialisasi Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.tag("KohiMangaApplication").i("Inisiasi Timber berhasil")
        }
        initCloudinary()
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
