package com.cop4485_volkan_bakir.hotmatch

import android.content.Context
import java.io.File

class CameraRepository private constructor(context: Context) {
    private val filesDir = context.applicationContext.filesDir

    fun getImageFile(fileName: String): File = File(filesDir, fileName)

    companion object {
        private var INSTANCE: CameraRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CameraRepository(context)
            }
        }

        fun get(): CameraRepository {
            return INSTANCE ?: throw IllegalStateException("CameraRepository must be initialized")
        }
    }
}