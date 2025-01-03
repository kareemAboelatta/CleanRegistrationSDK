package com.kareem.registrationsdk.domain.repository

import com.kareem.registrationsdk.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {

    suspend fun insertUser(user: UserModel): Long

    suspend fun deleteUser(user: UserModel)

    fun getAllUsers(): Flow<List<UserModel>>
}