package com.cop4485_volkan_bakir.hotmatch

import androidx.lifecycle.ViewModel
import java.io.File

class CameraViewModel: ViewModel() {
    private val cameraRepository = CameraRepository.get()
    fun getImageFile(fileName: String): File {
        return cameraRepository.getImageFile(fileName)
    }
}