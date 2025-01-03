package com.kareem.registrationsdk.domain.usecases.validation

import com.kareem.registrationsdk.domain.usecase.validation.ValidateUsernameUseCase
import com.kareem.registrationsdk.domain.usecase.validation.core.ValidationErrorType
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ValidateUsernameUseCaseTest {

    private lateinit var validateUsernameUseCase: ValidateUsernameUseCase

    @Before
    fun setUp() {
        validateUsernameUseCase = ValidateUsernameUseCase()
    }

    @Test
    fun `given an empty username when validating then return FieldEmpty error`() {
        // Arrange
        val input = ""

        // Act
        val result = validateUsernameUseCase.validate(input)

        // Assert
        assertEquals(false, result.isSuccessful)
        assertEquals(ValidationErrorType.FieldEmpty, result.errorType)
    }

    @Test
    fun `given a username containing digits when validating then return InvalidFormat error`() {
        // Arrange
        val input = "John123"

        // Act
        val result = validateUsernameUseCase.validate(input)

        // Assert
        assertEquals(false, result.isSuccessful)
        assertEquals(ValidationErrorType.InvalidFormat, result.errorType)
    }

    @Test
    fun `given a valid username when validating then return success`() {
        // Arrange
        val input = "JohnDoe"

        // Act
        val result = validateUsernameUseCase.validate(input)

        // Assert
        assertEquals(true, result.isSuccessful)
        assertEquals(null, result.errorType)
    }
}
