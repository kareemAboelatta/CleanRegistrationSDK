package com.kareem.registrationsdk.data.repository

import com.kareem.registrationsdk.data.local.UserDao
import com.kareem.registrationsdk.data.mapper.toUserEntity
import com.kareem.registrationsdk.data.mapper.toUserModel
import com.kareem.registrationsdk.domain.model.UserModel
import com.kareem.registrationsdk.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : RegisterRepository {

    override suspend fun insertUser(user: UserModel) {
        userDao.insertUser(user.toUserEntity())
    }

    override suspend fun deleteUser(user: UserModel) {
        userDao.deleteUser(user.toUserEntity())
    }

    override fun getAllUsers(): Flow<List<UserModel>> {
        return userDao.getAllUsers().map { entities ->
            entities.map { it.toUserModel() }
        }
    }
}
