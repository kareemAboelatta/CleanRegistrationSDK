package com.kareem.registrationsdk.presentation.screens.register.second_step_screen.viewmodel

import com.kareem.registrationsdk.detection.tasks.DetectionStep

sealed class SecondStepEvent {
    data class OnFlowStepStarted(val step: DetectionStep, val index: Int) : SecondStepEvent()
    data class OnFlowStepFinished(val step: DetectionStep, val isLastStep: Boolean) :
        SecondStepEvent()

    data class OnFlowError(val message: String) : SecondStepEvent()
    data class OnPhotoCaptured(val photoPath: String) : SecondStepEvent()
    data class OnDetectionStepTypeChanged(val type: SecondStepState.DetectionStepType) :
        SecondStepEvent()
}