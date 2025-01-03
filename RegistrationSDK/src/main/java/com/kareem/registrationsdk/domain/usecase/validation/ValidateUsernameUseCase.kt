package com.kareem.registrationsdk.domain.usecase.validation

import com.kareem.registrationsdk.domain.usecase.validation.core.BaseValidationUseCase
import com.kareem.registrationsdk.domain.usecase.validation.core.ValidationErrorType
import com.kareem.registrationsdk.domain.usecase.validation.core.ValidationResult
import javax.inject.Inject

class ValidateUsernameUseCase @Inject constructor() : BaseValidationUseCase<String>() {

    override fun validate(input: String): ValidationResult {
        // 1) Check if empty
        if (input.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorType = ValidationErrorType.FieldEmpty
            )
        }

        // 2) Ensure no digits
        val containsDigit = input.any { it.isDigit() }
        if (containsDigit) {
            return ValidationResult(
                isSuccessful = false,
                errorType = ValidationErrorType.InvalidFormat
            )
        }

        // 3) Success
        return ValidationResult(isSuccessful = true)
    }
}
