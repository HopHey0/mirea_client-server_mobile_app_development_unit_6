package com.hophey.pract1.domain.useCase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.OutputStream
import java.net.URL

class SavePhotoUseCase {
    suspend operator fun invoke(downloadUrl: String, outputStream: OutputStream) {
        withContext(Dispatchers.IO) {
            val url = URL(downloadUrl)
            val connection = url.openConnection()
            connection.connect()
            connection.getInputStream().use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }
        }
    }
}