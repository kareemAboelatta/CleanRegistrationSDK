package com.kareem.registrationsdk.domain.usecase

import com.kareem.registrationsdk.domain.model.UserModel
import com.kareem.registrationsdk.domain.repository.RegisterRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: RegisterRepository
) {
    suspend operator fun invoke(user: UserModel) = repository.insertUser(user)

}