package com.kareem.registrationsdk.domain.usecase.validation.core

// ValidationErrorType.kt
enum class ValidationErrorType {
    FieldEmpty,
    InvalidFormat
}

// ValidationResult.kt
data class ValidationResult(
    val isSuccessful: Boolean ,
    val errorType: ValidationErrorType? = null
)


