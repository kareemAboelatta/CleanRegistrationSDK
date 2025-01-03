package com.kareem.registrationsdk.presentation.core.navigation

import kotlinx.serialization.Serializable

object Screens {

    @Serializable
    data object RegistrationNavigation{
        @Serializable
        data object FirstStepScreen

        @Serializable
        data object SecondStepScreen

    }

    @Serializable
    data object RegistrationSuccessDialog

    @Serializable
    data object UsersListScreen

}