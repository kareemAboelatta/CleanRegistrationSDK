package com.kareem.registrationsdk.detection.tasks

import com.google.mlkit.vision.face.Face

/**
 * A detection step that checks if the user has blinked.
 */
class EyesBlinkDetectionStep(
    private val eyeOpenThreshold: Float = 0.3f // Probability threshold to consider eyes closed
) : DetectionStep {

    override val stepTitle: String = "Eyes Blink Capture"
    override val stepDescription: String = "Please blink your eyes"
    override var isComplete: Boolean = false
        private set


    override fun onBegin() {
        isComplete = false

    }

    override fun evaluate(face: Face, frameTimestampMs: Long): Boolean {
        val leftEyeOpenProb = face.leftEyeOpenProbability ?: 0f
        val rightEyeOpenProb = face.rightEyeOpenProbability ?: 0f
        val avgEyeOpenProb = (leftEyeOpenProb + rightEyeOpenProb) / 2

        if (avgEyeOpenProb < eyeOpenThreshold) {
            isComplete = true
            return true
        }

        return false
    }
}
