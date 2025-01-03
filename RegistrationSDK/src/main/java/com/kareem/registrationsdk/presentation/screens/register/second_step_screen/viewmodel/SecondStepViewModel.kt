package com.kareem.registrationsdk.presentation.screens.register.second_step_screen.viewmodel

import android.util.Log
import com.kareem.registrationsdk.presentation.core.base.BaseViewModel
import com.kareem.registrationsdk.presentation.core.base.UiEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed class SecondStepUiEffect : UiEffect() {
    data object CapturePhoto : SecondStepUiEffect()  // signals that all steps are complete
    data class PhotoCaptured(val photoPath: String) : SecondStepUiEffect()  // signals that all steps are complete
    data class ShowFlowError(val message: String) : SecondStepUiEffect()
}


@HiltViewModel
class SecondStepViewModel @Inject constructor() : BaseViewModel<SecondStepState, SecondStepEvent>(
    state = SecondStepState(),
    event = null
) {

    override fun onEvent(event: SecondStepEvent) {
        when (event) {
            is SecondStepEvent.OnFlowStepStarted -> {
                // Update the status message, for example
                Log.d("TAG", "SecondStepViewModel: onEvent: OnFlowStepStarted");
            }

            is SecondStepEvent.OnFlowStepFinished -> {
                // If it's the last step, we trigger the photo capture
                if (event.isLastStep) {
                    setEffect { SecondStepUiEffect.CapturePhoto }
                } else {
                    // You can update status message for the next step
                    state = state.copy(statusMessage = "Detecting your smile…")
                }
            }

            is SecondStepEvent.OnFlowError -> {
                // show error UI or Toast
                setEffect { SecondStepUiEffect.ShowFlowError(event.message) }
            }

            is SecondStepEvent.OnPhotoCaptured -> {
                setEffect { SecondStepUiEffect.PhotoCaptured(event.photoPath) }
            }
        }
    }


}
