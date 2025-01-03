package com.kareem.registrationsdk.detection.tasks

import com.google.mlkit.vision.face.Face
import kotlin.math.abs

/**
 * A simple detection step that checks if the user is smiling sufficiently
 * and their head orientation is not too extreme.
 */
class SmileDetectionStep(
    private val minSmileProbability: Float = 0.80f,
    private val maxRotationDegrees: Float = 20f
) : DetectionStep {

    override val stepTitle: String = "Smile Capture"
    override val stepDescription: String = "Please smile at the camera."
    override var isComplete: Boolean = false
        private set

    override fun onBegin() {
        // Reset state whenever this step starts
        isComplete = false
    }

    override fun evaluate(face: Face, frameTimestampMs: Long): Boolean {
        val smilingProb = face.smilingProbability ?: 0f
        val angleY = face.headEulerAngleY // yaw
        val angleZ = face.headEulerAngleZ // roll

        val isSmiling = smilingProb >= minSmileProbability
        val isFacingStraight = abs(angleY) <= maxRotationDegrees && abs(angleZ) <= maxRotationDegrees

        isComplete = isSmiling && isFacingStraight
        return isComplete
    }
}
