package com.lucas.petros.spotfylab

import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SpotfyLabApplication : Application() {

    companion object {
        private lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}