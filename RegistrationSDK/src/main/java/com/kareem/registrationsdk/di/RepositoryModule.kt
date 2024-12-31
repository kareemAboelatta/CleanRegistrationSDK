package com.kareem.registrationsdk.di

import com.kareem.registrationsdk.data.local.UserDao
import com.kareem.registrationsdk.data.repository.RegisterRepositoryImpl
import com.kareem.registrationsdk.domain.repository.RegisterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideRegisterRepository(
        userDao: UserDao
    ): RegisterRepository {
        return RegisterRepositoryImpl(userDao)
    }
}
