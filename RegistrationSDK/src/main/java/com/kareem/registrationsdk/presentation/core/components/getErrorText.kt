package com.kareem.registrationsdk.presentation.core.components

import com.kareem.registrationsdk.domain.usecase.validation.core.ValidationErrorType
import com.kareem.registrationsdk.domain.usecase.validation.core.ValidationResult

/**
 * Returns a non-localized error message (in English) based on [ValidationResult].
 * 
 * @param errorItem The result of a validation check.
 * @param fieldName The name of the field for display in messages (e.g., "Phone Number").
 * @param invalidFormatMsg Optional custom message if [ValidationErrorType.InvalidFormat] is encountered.
 */
fun getErrorText(
    errorItem: ValidationResult?,
    fieldName: String,
    invalidFormatMsg: String? = null,
): String? {
    if (errorItem == null || errorItem.isSuccessful) return null

    return when (errorItem.errorType) {
        ValidationErrorType.FieldEmpty -> {
            "Please enter $fieldName."
        }
        ValidationErrorType.InvalidFormat -> {
            invalidFormatMsg ?: "Please enter a valid $fieldName."
        }
        else -> null
    }
}
