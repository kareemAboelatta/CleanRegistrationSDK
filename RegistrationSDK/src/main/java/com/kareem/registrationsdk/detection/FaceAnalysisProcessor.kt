package com.kareem.registrationsdk.detection

import android.os.Handler
import android.os.Looper
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.core.os.ExecutorCompat
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions

/**
 * Bridges CameraX frames to ML Kit face detection, then forwards results
 * to the FaceWorkflow for step-by-step checks.
 */
class FaceAnalysisProcessor(
    private val workflow: FaceWorkflow
) : ImageAnalysis.Analyzer {

    private val faceDetectorOptions = FaceDetectorOptions.Builder()
        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST) //PERFORMANCE_MODE_ACCURATE
        .enableTracking() 
        .build()

    private val faceDetector = FaceDetection.getClient(faceDetectorOptions)

    // MlKitAnalyzer can chain multiple detectors, but here we only use faceDetector
    private val mlKitAnalyzer = MlKitAnalyzer(
        listOf(faceDetector),
        ImageAnalysis.COORDINATE_SYSTEM_ORIGINAL,
        ExecutorCompat.create(Handler(Looper.getMainLooper()))
    ) { result ->
        val faces: List<Face>? = result.getValue(faceDetector)
        val timestamp = result.timestamp
        workflow.processFrame(faces, timestamp)
    }

    override fun analyze(image: ImageProxy) {
        mlKitAnalyzer.analyze(image)
    }
}
