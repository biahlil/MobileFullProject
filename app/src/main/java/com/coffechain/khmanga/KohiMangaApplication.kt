package com.coffechain.khmanga

import android.app.Application
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
    }

}