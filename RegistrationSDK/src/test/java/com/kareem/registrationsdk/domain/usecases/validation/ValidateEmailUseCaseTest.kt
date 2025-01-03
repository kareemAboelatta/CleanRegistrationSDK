package com.kareem.registrationsdk.domain.usecases.validation

import com.kareem.registrationsdk.domain.usecase.validation.ValidateEmailUseCase
import com.kareem.registrationsdk.domain.usecase.validation.core.ValidationErrorType
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ValidateEmailUseCaseTest {

    private lateinit var validateEmailUseCase: ValidateEmailUseCase

    @Before
    fun setUp() {
        validateEmailUseCase = ValidateEmailUseCase()
    }

    @Test
    fun `given empty email when validating then return FieldEmpty error`() {
        // Arrange
        val input = ""

        // Act
        val result = validateEmailUseCase.validate(input)

        // Assert
        assertEquals(false, result.isSuccessful)
        assertEquals(ValidationErrorType.FieldEmpty, result.errorType)
    }

    @Test
    fun `given email without at symbol when validating then return InvalidFormat error`() {
        // Arrange
        val input = "example.com"

        // Act
        val result = validateEmailUseCase.validate(input)

        // Assert
        assertEquals(false, result.isSuccessful)
        assertEquals(ValidationErrorType.InvalidFormat, result.errorType)
    }

    @Test
    fun `given email with invalid domain when validating then return InvalidFormat error`() {
        // This simulates something like "username@.com" or a missing domain part
        val input = "username@"

        val result = validateEmailUseCase.validate(input)

        assertEquals(false, result.isSuccessful)
        assertEquals(ValidationErrorType.InvalidFormat, result.errorType)
    }

    @Test
    fun `given valid email with subdomain when validating then return success`() {
        // e.g. name@sub.example.co.uk
        val input = "john.doe@sub.example.co.uk"

        val result = validateEmailUseCase.validate(input)

        assertEquals(true, result.isSuccessful)
        assertEquals(null, result.errorType)
    }

    @Test
    fun `given valid normal email when validating then return success`() {
        val input = "john@example.com"

        val result = validateEmailUseCase.validate(input)

        assertEquals(true, result.isSuccessful)
        assertEquals(null, result.errorType)
    }
}
