package com.kareem.registrationsdk.domain.usecases

import com.kareem.registrationsdk.domain.model.UserModel
import com.kareem.registrationsdk.domain.repository.RegisterRepository
import com.kareem.registrationsdk.domain.usecase.GetAllUsersUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class GetAllUsersUseCaseTest {

    @Mock
    lateinit var repository: RegisterRepository

    private lateinit var getAllUsersUseCase: GetAllUsersUseCase

    @Before
    fun setUp() {
        getAllUsersUseCase = GetAllUsersUseCase(repository)
    }

    @Test
    fun `when invoke then calls getAllUsers in repository and returns flow`() = runTest {
        // Arrange
        val mockUsers = listOf(
            UserModel(
                id = 1,
                username = "John",
                phone = "1234567890",
                email = "john@example.com",
                password = "Abc@1234",
                userImage = null
            ),
            UserModel(
                id = 2,
                username = "Jane",
                phone = "0987654321",
                email = "jane@example.com",
                password = "Abc@1234",
                userImage = null
            )
        )
        val expectedFlow: Flow<List<UserModel>> = flowOf(mockUsers)

        // Stub repository response
        whenever(repository.getAllUsers()).thenReturn(expectedFlow)

        // Act
        val resultFlow = getAllUsersUseCase()
        val resultList = resultFlow.first() // collect first emission

        // Assert
        verify(repository, times(1)).getAllUsers()
        assertEquals(mockUsers.size, resultList.size)
        assertEquals(mockUsers[0].username, resultList[0].username)
        assertEquals(mockUsers[1].email, resultList[1].email)
    }
}
