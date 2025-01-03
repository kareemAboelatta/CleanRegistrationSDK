package com.kareem.registrationsdk.detection.tasks

import com.google.mlkit.vision.face.Face

/**
 * Represents a single detection requirement in the face analysis workflow.
 * E.g.: "Smile", "Blink", "Rotate Head", etc.
 */
interface DetectionStep  {
    val stepTitle: String
    val stepDescription: String
    val isComplete: Boolean

    /** Called once at the beginning of this step. */
    fun onBegin()

    /**
     * Evaluates the current Face data to decide if this step is fulfilled.
     * Returns true if the detection requirement is satisfied.
     */
    fun evaluate(face: Face, frameTimestampMs: Long): Boolean
}
