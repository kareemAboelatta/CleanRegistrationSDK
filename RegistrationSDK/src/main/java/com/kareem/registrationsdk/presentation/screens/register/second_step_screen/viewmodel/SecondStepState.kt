package com.kareem.registrationsdk.presentation.screens.register.second_step_screen.viewmodel

import com.kareem.registrationsdk.detection.tasks.DetectionStep
import com.kareem.registrationsdk.detection.tasks.EyesBlinkDetectionStep
import com.kareem.registrationsdk.detection.tasks.SmileDetectionStep

data class SecondStepState(
    val statusMessage: String = "Please align your face",
    val detectionMethodType: DetectionStepType = DetectionStepType.Smile
){

    enum class DetectionStepType(val detectionStep: DetectionStep) {
        Smile(SmileDetectionStep()),
        Blink(EyesBlinkDetectionStep())
    }
}
