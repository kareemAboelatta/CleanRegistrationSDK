package com.kareem.registrationsdk.domain.usecases

import com.kareem.registrationsdk.domain.model.UserModel
import com.kareem.registrationsdk.domain.repository.RegisterRepository
import com.kareem.registrationsdk.domain.usecase.RegisterUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class RegisterUseCaseTest {

    @Mock
    lateinit var repository: RegisterRepository

    private lateinit var registerUseCase: RegisterUseCase

    @Before
    fun setUp() {
        registerUseCase = RegisterUseCase(repository)
    }

    @Test
    fun `when invoke with valid user then calls insertUser in repository`() = runTest {
        // Arrange
        val user = UserModel(
            id = null,
            username = "John",
            phone = "1234567890",
            email = "john@example.com",
            password = "Abc@1234",
            userImage = null
        )

        // Act
        registerUseCase.invoke(user)

        // Assert
        verify(repository, times(1)).insertUser(user)
    }
}
