package com.kareem.registrationsdk.presentation.screens.register.first_step_screen.viewmodel

sealed class FirstStepEvent {
    data object Idle : FirstStepEvent()


    data class OnNameChanged(val name: String) : FirstStepEvent()
    data class OnPhoneChanged(val phone: String) : FirstStepEvent()
    data class OnEmailChanged(val email: String) : FirstStepEvent()
    data class OnPasswordChanged(val password: String) : FirstStepEvent()

    data object Submit : FirstStepEvent()

    data object NavigateToSecondScreen : FirstStepEvent()

}
