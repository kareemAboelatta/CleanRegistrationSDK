package com.kareem.registrationsdk.domain.usecase.validation

import com.kareem.registrationsdk.domain.usecase.validation.core.BaseValidationUseCase
import com.kareem.registrationsdk.domain.usecase.validation.core.ValidationErrorType
import com.kareem.registrationsdk.domain.usecase.validation.core.ValidationResult
import javax.inject.Inject

class ValidatePhoneUseCase @Inject constructor() : BaseValidationUseCase<String>() {

    /*
     * A phone regex that:
     *  - Allows an optional '+'.
     *  - Allows digits and optional dashes (e.g., '123-456-789').
     *  - Ensures at least 9 digits total.
     *  - Disallows letters or other special chars (besides dash).
     *
     * Explanation:
     *   ^\\+?           : optional '+' at the start
     *   (?:\\d[ -]?)*   : a group of digits optionally followed by a space or dash, repeated
     *   \\d{8,}         : ensuring at least 9 digits total (the first digit + 8 more)
     *   $               : end of string
     */
    private val phoneRegex = Regex("^\\+?(?:\\d[ -]?)*\\d{8,}\$")

    override fun validate(input: String): ValidationResult {
        // 1) Check if empty
        if (input.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorType = ValidationErrorType.FieldEmpty
            )
        }

        // 2) Format check
        if (!phoneRegex.matches(input)) {
            return ValidationResult(
                isSuccessful = false,
                errorType = ValidationErrorType.InvalidFormat
            )
        }

        // 3) Success
        return ValidationResult(isSuccessful = true)
    }
}
