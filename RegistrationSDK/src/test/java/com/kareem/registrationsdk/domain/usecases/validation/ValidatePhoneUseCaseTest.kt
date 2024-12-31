package com.kareem.registrationsdk.domain.usecases.validation

import com.kareem.registrationsdk.domain.usecase.validation.ValidatePhoneUseCase
import com.kareem.registrationsdk.domain.usecase.validation.core.ValidationErrorType
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ValidatePhoneUseCaseTest {

    private lateinit var validatePhoneUseCase: ValidatePhoneUseCase

    @Before
    fun setUp() {
        validatePhoneUseCase = ValidatePhoneUseCase()
    }

    @Test
    fun `given empty phone when validating then return FieldEmpty error`() {
        val input = ""

        val result = validatePhoneUseCase.validate(input)

        assertEquals(false, result.isSuccessful)
        assertEquals(ValidationErrorType.FieldEmpty, result.errorType)
    }

    @Test
    fun `given phone with too few digits when validating then return InvalidFormat error`() {
        // Suppose we consider < 9 digits invalid
        val input = "12345"

        val result = validatePhoneUseCase.validate(input)

        assertEquals(false, result.isSuccessful)
        assertEquals(ValidationErrorType.InvalidFormat, result.errorType)
    }

    @Test
    fun `given phone with letters when validating then return InvalidFormat error`() {
        val input = "1234ABCD"

        val result = validatePhoneUseCase.validate(input)

        assertEquals(false, result.isSuccessful)
        assertEquals(ValidationErrorType.InvalidFormat, result.errorType)
    }

    @Test
    fun `given phone with special chars except plus or dash when validating then return InvalidFormat error`() {
        // e.g. parentheses or other special characters might be disallowed
        val input = "123(456)78"

        val result = validatePhoneUseCase.validate(input)

        assertEquals(false, result.isSuccessful)
        assertEquals(ValidationErrorType.InvalidFormat, result.errorType)
    }

    @Test
    fun `given phone with plus sign in front and at least 9 digits when validating then return success`() {
        // e.g. +123456789
        val input = "+123456789"

        val result = validatePhoneUseCase.validate(input)

        assertEquals(true, result.isSuccessful)
        assertEquals(null, result.errorType)
    }

    @Test
    fun `given phone with dash in the middle but enough digits when validating then return success`() {
        // e.g. 123-456-7890
        val input = "123-456-7890"

        val result = validatePhoneUseCase.validate(input)

        assertEquals(true, result.isSuccessful)
        assertEquals(null, result.errorType)
    }
}
