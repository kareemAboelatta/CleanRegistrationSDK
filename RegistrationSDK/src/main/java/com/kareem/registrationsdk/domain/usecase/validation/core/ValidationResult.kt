package com.kareem.registrationsdk.domain.usecase.validation.core

enum class ValidationErrorType {
    FieldEmpty,
    InvalidFormat
}

data class ValidationResult(
    val isSuccessful: Boolean ,
    val errorType: ValidationErrorType? = null
)


