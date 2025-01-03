package com.kareem.registrationsdk.domain.usecase

import com.kareem.registrationsdk.domain.model.UserModel
import com.kareem.registrationsdk.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
    private val repository: RegisterRepository
) {
    operator fun invoke(): Flow<List<UserModel>> {
        return repository.getAllUsers()
    }
}
