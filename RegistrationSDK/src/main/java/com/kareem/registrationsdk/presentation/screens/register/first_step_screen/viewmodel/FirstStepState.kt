package com.kareem.registrationsdk.presentation.screens.register.first_step_screen.viewmodel

import com.kareem.registrationsdk.domain.usecase.validation.core.ValidationResult

data class FirstStepState(
    val name: String = "",
    val nameError: ValidationResult? = null,

    val email: String = "",
    val emailError: ValidationResult? = null,

    val phone: String = "",
    val phoneError: ValidationResult? = null,

    val password: String = "",
    val passwordError: ValidationResult? = null
)