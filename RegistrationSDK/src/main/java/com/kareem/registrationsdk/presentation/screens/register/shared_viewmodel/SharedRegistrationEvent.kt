package com.kareem.registrationsdk.presentation.screens.register.shared_viewmodel

import com.kareem.registrationsdk.domain.model.UserModel

sealed class SharedRegistrationEvent {
    data object SaveUserData : SharedRegistrationEvent()
    data object Idle : SharedRegistrationEvent()
    data class OnUserModelChanged(val userModel: UserModel) : SharedRegistrationEvent()
    data class OnImageChanged(val image: String) : SharedRegistrationEvent()
}
