package com.kareem.registrationsdk.domain.usecase.validation

import com.kareem.registrationsdk.domain.usecase.validation.core.BaseValidationUseCase
import com.kareem.registrationsdk.domain.usecase.validation.core.ValidationErrorType
import com.kareem.registrationsdk.domain.usecase.validation.core.ValidationResult
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor() : BaseValidationUseCase<String>() {


    private val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")

    override fun validate(input: String): ValidationResult {
        // 1) Check if empty
        if (input.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorType = ValidationErrorType.FieldEmpty
            )
        }

        // 2) Check format
        if (!emailRegex.matches(input)) {
            return ValidationResult(
                isSuccessful = false,
                errorType = ValidationErrorType.InvalidFormat
            )
        }

        // 3) Success
        return ValidationResult(isSuccessful = true)
    }
}
