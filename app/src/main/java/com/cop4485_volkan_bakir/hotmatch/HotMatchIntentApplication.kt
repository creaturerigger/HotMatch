package com.cop4485_volkan_bakir.hotmatch

import android.app.Application

class HotMatchIntentApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        CameraRepository.initialize(this)
    }
}