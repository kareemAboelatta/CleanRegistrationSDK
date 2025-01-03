package com.kareem.registrationsdk.detection

import com.google.mlkit.vision.face.Face
import com.kareem.registrationsdk.detection.tasks.DetectionStep

/**
 * Manages a sequence of detection steps. On each new camera frame, it tries
 * to advance through these steps. If a step is completed, it moves to the next one.
 */
class FaceWorkflow(
    private val steps: List<DetectionStep>
) {

    /** FaceWorkflowListener to handle success/failure events, UI feedback, etc. */
    interface FaceWorkflowListener {
        fun onStepStarted(step: DetectionStep, stepIndex: Int)
        fun onStepCompleted(step: DetectionStep, isFinalStep: Boolean)
        fun onWorkflowError(error: WorkflowError)
    }

    /** Possible errors while processing the workflow. Add more if needed. */
    sealed class WorkflowError {
        data object NoFacesFound : WorkflowError()
        data object MultipleFacesFound : WorkflowError()
        // Could add e.g. OutOfBounds, PoorLighting, etc.
    }

    private var currentStepIndex = 0
    private var listener: FaceWorkflowListener? = null

    fun setListener(listener: FaceWorkflowListener) {
        this.listener = listener
    }

    /**
     * Process the list of detected faces. Checks for errors, then attempts to
     * evaluate the current detection step. If it passes, proceed to the next step.
     */
    fun processFrame(faces: List<Face>?, frameTimestampMs: Long) {
        if (faces.isNullOrEmpty()) {
            listener?.onWorkflowError(WorkflowError.NoFacesFound)
            resetWorkflow()
            return
        }
        if (faces.size > 1) {
            listener?.onWorkflowError(WorkflowError.MultipleFacesFound)
            resetWorkflow()
            return
        }

        val singleFace = faces.first()

        val currentStep = steps.getOrNull(currentStepIndex) ?: return

        // If we're just arriving on this step, call onBegin + onStepStarted
        if (!currentStep.isComplete) {
            currentStep.onBegin()
            listener?.onStepStarted(currentStep, currentStepIndex)
        }

        // Evaluate
        val stepPassed = currentStep.evaluate(singleFace, frameTimestampMs)
        if (stepPassed) {
            listener?.onStepCompleted(
                currentStep,
                isFinalStep = (currentStepIndex == steps.lastIndex)
            )
            currentStepIndex++
            // If there are more steps, the next step will begin on the next frame
        }
    }

    /** Reset the workflow to the first step (e.g. after an error). */
    private fun resetWorkflow() {
        currentStepIndex = 0
        steps.forEach { step ->
            // If you had more state in each step, reset it here
        }
    }
}
