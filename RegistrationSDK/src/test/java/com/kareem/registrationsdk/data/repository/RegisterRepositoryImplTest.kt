package com.kareem.registrationsdk.data.repository

import com.kareem.registrationsdk.data.local.UserDao
import com.kareem.registrationsdk.data.model.UserEntity
import com.kareem.registrationsdk.domain.model.UserModel
import com.kareem.registrationsdk.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import com.google.common.truth.Truth.assertThat

@RunWith(MockitoJUnitRunner::class)
class RegisterRepositoryImplTest {

    @Mock
    lateinit var userDao: UserDao

    private lateinit var registerRepository: RegisterRepository

    @Before
    fun setUp() {
        registerRepository = RegisterRepositoryImpl(userDao)
    }

    // -------------------------------
    // 1) insertUser
    // -------------------------------
    @Test
    fun `insertUser should call userDaoinsertUser with mapped entity`() = runTest {
        // Arrange
        val domainUser = UserModel(
            id = null,
            username = "JohnDoe",
            phone = "123456789",
            email = "john@example.com",
            password = "Abc@1234",
            userImage = null
        )

        // Act
        registerRepository.insertUser(domainUser)

        // Assert
        argumentCaptor<UserEntity>().apply {
            verify(userDao, times(1)).insertUser(capture())
            val capturedEntity = firstValue
            // Compare fields
            assertThat(capturedEntity.username).isEqualTo(domainUser.username)
            assertThat(capturedEntity.phone).isEqualTo(domainUser.phone)
            assertThat(capturedEntity.email).isEqualTo(domainUser.email)
            assertThat(capturedEntity.password).isEqualTo(domainUser.password)
            assertThat(capturedEntity.userImage).isEqualTo(domainUser.userImage)
        }
    }

    // -------------------------------
    // 2) deleteUser
    // -------------------------------
    @Test
    fun `deleteUser should call userDaoDeleteUser with mapped entity`() = runTest {
        // Arrange
        val domainUser = UserModel(
            id = 1,
            username = "Alice",
            phone = "987654321",
            email = "alice@example.com",
            password = "Abc@1234",
            userImage = null
        )

        // Act
        registerRepository.deleteUser(domainUser)

        // Assert
        argumentCaptor<UserEntity>().apply {
            verify(userDao, times(1)).deleteUser(capture())
            val capturedEntity = firstValue
            assertThat(capturedEntity.id).isEqualTo(domainUser.id)
            assertThat(capturedEntity.username).isEqualTo("Alice")
            assertThat(capturedEntity.phone).isEqualTo("987654321")
            assertThat(capturedEntity.email).isEqualTo("alice@example.com")
            assertThat(capturedEntity.password).isEqualTo("Abc@1234")
            assertThat(capturedEntity.userImage).isNull()
        }
    }

    // -------------------------------
    // 3) getAllUsers
    // -------------------------------
    @Test
    fun `getAllUsers should return domain models mapped from entity`() = runTest {
        // Arrange
        val userEntity1 = UserEntity(
            id = 1,
            username = "User1",
            phone = "111111111",
            email = "user1@example.com",
            password = "Abc@1234",
            userImage = null
        )
        val userEntity2 = UserEntity(
            id = 2,
            username = "User2",
            phone = "222222222",
            email = "user2@example.com",
            password = "Abc@1234",
            userImage = null
        )

        // Mock DAO to return a flow of [userEntity1, userEntity2]
        whenever(userDao.getAllUsers()).thenReturn(flowOf(listOf(userEntity1, userEntity2)))

        // Act
        val result = registerRepository.getAllUsers().first()

        // Assert
        // We expect a list of 2 domain users mapped from the 2 entities
        assertThat(result).hasSize(2)

        val firstUser = result[0]
        val secondUser = result[1]

        assertThat(firstUser.id).isEqualTo(1)
        assertThat(firstUser.username).isEqualTo("User1")
        assertThat(firstUser.phone).isEqualTo("111111111")
        assertThat(firstUser.email).isEqualTo("user1@example.com")
        assertThat(firstUser.password).isEqualTo("Abc@1234")
        assertThat(firstUser.userImage).isNull()

        assertThat(secondUser.id).isEqualTo(2)
        assertThat(secondUser.username).isEqualTo("User2")
        assertThat(secondUser.phone).isEqualTo("222222222")
        assertThat(secondUser.email).isEqualTo("user2@example.com")
        assertThat(secondUser.password).isEqualTo("Abc@1234")
        assertThat(secondUser.userImage).isNull()
    }
}
