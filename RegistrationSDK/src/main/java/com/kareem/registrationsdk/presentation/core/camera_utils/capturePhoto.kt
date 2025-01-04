package com.kareem.registrationsdk.presentation.core.camera_utils

import android.content.Context
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import java.io.File

/**
 * Captures a photo using the given [cameraController].
 * Writes it to a [File] in app's data directory and invokes [onSaved] callback.
 */
fun LifecycleCameraController.capturePhoto(
    context: Context,
    onSaved: (File) -> Unit
) {
    val outputFile = File(context.dataDir, "captured_${System.currentTimeMillis()}.jpg")
    val outputOptions = ImageCapture.OutputFileOptions.Builder(outputFile).build()

    this.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onError(exception: ImageCaptureException) {
                // Handle error logging or show a user message
                exception.printStackTrace()
                Log.e("Tag", "Photo capture failed", exception)
            }

            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                onSaved(outputFile)
            }
        }
    )
}
