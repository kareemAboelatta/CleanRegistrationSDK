package com.kareem.registrationsdk.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.kareem.registrationsdk.data.model.UserEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class UserDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var userDao: UserDao

    @Before
    fun setUp() {
        // Use an in-memory database. Data disappears after the test completes.
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .allowMainThreadQueries() // OK for small tests; avoid on production code
            .build()

        userDao = database.userDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertUserAndRetrieveIt() = runTest {
        // Arrange
        val user = UserEntity(
            id = null,
            username = "JohnDoe",
            phone = "123456789",
            email = "john@example.com",
            password = "Abc@1234",
            userImage = null
        )

        // Act
        userDao.insertUser(user)
        val allUsers = userDao.getAllUsers().first() // Collect the first emission from Flow

        // Assert
        assertThat(allUsers).hasSize(1)
        val insertedUser = allUsers[0]
        assertThat(insertedUser.username).isEqualTo("JohnDoe")
        assertThat(insertedUser.phone).isEqualTo("123456789")
        assertThat(insertedUser.email).isEqualTo("john@example.com")
        assertThat(insertedUser.password).isEqualTo("Abc@1234")
        assertThat(insertedUser.userImage).isNull() // or whatever logic you expect
    }

    @Test
    fun deleteUserRemovesItFromDB() = runTest {
        // Arrange
        val user = UserEntity(
            id = 1, // or null if autogenerating
            username = "Alice",
            phone = "987654321",
            email = "alice@example.com",
            password = "Abc@1234",
            userImage = null
        )
        userDao.insertUser(user)

        // Act
        userDao.deleteUser(user)
        val allUsers = userDao.getAllUsers().first()

        // Assert
        assertThat(allUsers).isEmpty()
    }

    @Test
    fun insertMultipleUsersAndVerifyCount() = runTest {
        // Arrange
        val user1 = UserEntity(
            id = null,
            username = "User1",
            phone = "111111111",
            email = "user1@example.com",
            password = "Abc@1234",
            userImage = null
        )
        val user2 = UserEntity(
            id = null,
            username = "User2",
            phone = "222222222",
            email = "user2@example.com",
            password = "Abc@1234",
            userImage = null
        )

        // Act
        userDao.insertUser(user1)
        userDao.insertUser(user2)
        val allUsers = userDao.getAllUsers().first()

        // Assert
        assertThat(allUsers).hasSize(2)

        assertThat(allUsers.map { it.username }).containsExactly("User1", "User2")
    }
}
