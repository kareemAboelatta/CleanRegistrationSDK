// BaseValidationUseCase.kt
package com.kareem.registrationsdk.domain.usecase.validation.core

abstract class BaseValidationUseCase<T> {

    // Abstract function to be implemented by each validator.
    abstract fun validate(input: T): ValidationResult


}
