package com.kareem.registrationsdk.domain.usecase.validation

import com.kareem.registrationsdk.domain.usecase.validation.core.BaseValidationUseCase
import com.kareem.registrationsdk.domain.usecase.validation.core.ValidationErrorType
import com.kareem.registrationsdk.domain.usecase.validation.core.ValidationResult
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() : BaseValidationUseCase<String>() {

    private val symbols = listOf('!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '_', '+', '=')

    override fun validate(input: String): ValidationResult {
        // 1) Check if empty
        if (input.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorType = ValidationErrorType.FieldEmpty
            )
        }

        // 2) Must contain at least one uppercase letter
        val hasCapitalLetter = input.any { it.isUpperCase() }
        if (!hasCapitalLetter) {
            return ValidationResult(
                isSuccessful = false,
                errorType = ValidationErrorType.InvalidFormat
            )
        }

        // 3) Must contain at least one lowercase letter
        val hasLowerCaseLetter = input.any { it.isLowerCase() }
        if (!hasLowerCaseLetter) {
            return ValidationResult(
                isSuccessful = false,
                errorType = ValidationErrorType.InvalidFormat
            )
        }

        // 4) Must contain at least one symbol
        val hasSymbol = input.any { it in symbols }
        if (!hasSymbol) {
            return ValidationResult(
                isSuccessful = false,
                errorType = ValidationErrorType.InvalidFormat
            )
        }

        // 5) Success
        return ValidationResult(isSuccessful = true)
    }
}
