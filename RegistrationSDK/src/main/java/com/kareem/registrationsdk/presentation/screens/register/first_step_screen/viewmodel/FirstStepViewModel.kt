package com.kareem.registrationsdk.presentation.screens.register.first_step_screen.viewmodel

import com.kareem.registrationsdk.domain.model.UserModel
import com.kareem.registrationsdk.domain.usecase.validation.ValidateEmailUseCase
import com.kareem.registrationsdk.domain.usecase.validation.ValidatePasswordUseCase
import com.kareem.registrationsdk.domain.usecase.validation.ValidatePhoneUseCase
import com.kareem.registrationsdk.domain.usecase.validation.ValidateUsernameUseCase
import com.kareem.registrationsdk.presentation.core.base.BaseViewModel
import com.kareem.registrationsdk.presentation.core.base.UiEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed class FirstStepUiEffect : UiEffect() {
    data class NavigateToSecondScreenToPickPhoto(val userModel: UserModel) : FirstStepUiEffect()
}

@HiltViewModel
class FirstStepViewModel @Inject constructor(
    private val validateNameUseCase: ValidateUsernameUseCase,
    private val validatePhoneUseCase: ValidatePhoneUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase

) : BaseViewModel<FirstStepState, FirstStepEvent>(FirstStepState(), FirstStepEvent.Idle) {


    override fun onEvent(event: FirstStepEvent) {
        when (event) {
            FirstStepEvent.Idle -> {}
            is FirstStepEvent.OnEmailChanged -> {
                state = state.copy(
                    email = event.email,
                    emailError = null
                )
            }

            is FirstStepEvent.OnNameChanged -> {
                state = state.copy(
                    name = event.name,
                    nameError = null
                )
            }

            is FirstStepEvent.OnPasswordChanged -> {
                state = state.copy(
                    password = event.password,
                    passwordError = null
                )
            }

            is FirstStepEvent.OnPhoneChanged -> {
                state = state.copy(
                    phone = event.phone,
                    phoneError = null
                )
            }

            FirstStepEvent.Submit -> {
                validateData()
            }

        }

    }

    private fun validateData() {

        val nameResult = validateNameUseCase.validate(state.name)
        val phoneResult = validatePhoneUseCase.validate(state.phone)
        val emailResult = validateEmailUseCase.validate(state.email)
        val passwordResult = validatePasswordUseCase.validate(state.password)

        val validations = listOf(nameResult, phoneResult, emailResult, passwordResult)
        val validationFailed = validations.any { !it.isSuccessful }

        if (validationFailed) {
            state = state.copy(
                nameError = nameResult,
                phoneError = phoneResult,
                emailError = emailResult,
                passwordError = passwordResult
            )
        } else {
            state = state.copy(
                nameError = null,
                phoneError = null,
                emailError = null,
                passwordError = null,
            )

            setEffect {
                FirstStepUiEffect.NavigateToSecondScreenToPickPhoto(
                    userModel = UserModel(
                        username = state.name,
                        phone = state.phone,
                        email = state.email,
                        password = state.password,
                        userImage = null
                    )
                )
            }
        }
    }
}



