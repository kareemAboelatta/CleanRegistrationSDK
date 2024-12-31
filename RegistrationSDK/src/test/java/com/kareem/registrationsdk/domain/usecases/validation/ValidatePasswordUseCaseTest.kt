package com.kareem.registrationsdk.domain.usecases.validation

import com.kareem.registrationsdk.domain.usecase.validation.ValidatePasswordUseCase
import com.kareem.registrationsdk.domain.usecase.validation.core.ValidationErrorType
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ValidatePasswordUseCaseTest {

    private lateinit var validatePasswordUseCase: ValidatePasswordUseCase

    @Before
    fun setUp() {
        validatePasswordUseCase = ValidatePasswordUseCase()
    }

    @Test
    fun `given an empty password when validating then return FieldEmpty error`() {
        // Arrange
        val input = ""

        // Act
        val result = validatePasswordUseCase.validate(input)

        // Assert
        assertEquals(false, result.isSuccessful)
        assertEquals(ValidationErrorType.FieldEmpty, result.errorType)
    }

    @Test
    fun `given a password with no capital letter when validating then return InvalidFormat error`() {
        // Arrange
        val input = "abc!123"

        // Act
        val result = validatePasswordUseCase.validate(input)

        // Assert
        assertEquals(false, result.isSuccessful)
        assertEquals(ValidationErrorType.InvalidFormat, result.errorType)
    }

    @Test
    fun `given a password with no lowercase letter when validating then return InvalidFormat error`() {
        // Arrange
        val input = "ABC!123"

        // Act
        val result = validatePasswordUseCase.validate(input)

        // Assert
        assertEquals(false, result.isSuccessful)
        assertEquals(ValidationErrorType.InvalidFormat, result.errorType)
    }

    @Test
    fun `given a password with no symbol when validating then return InvalidFormat error`() {
        // Arrange
        val input = "Abc1234"

        // Act
        val result = validatePasswordUseCase.validate(input)

        // Assert
        assertEquals(false, result.isSuccessful)
        assertEquals(ValidationErrorType.InvalidFormat, result.errorType)
    }

    @Test
    fun `given a valid password containing uppercase lowercase and symbol when validating then return success`() {
        // Arrange
        val input = "Abc@1234"

        // Act
        val result = validatePasswordUseCase.validate(input)

        // Assert
        assertEquals(true, result.isSuccessful)
        assertEquals(null, result.errorType)
    }
}
