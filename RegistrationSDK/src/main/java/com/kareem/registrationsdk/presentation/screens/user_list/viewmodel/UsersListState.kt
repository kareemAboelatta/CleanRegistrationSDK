package com.kareem.registrationsdk.presentation.screens.user_list.viewmodel

import com.kareem.registrationsdk.domain.model.UserModel

data class UsersListState(
    val users: List<UserModel> = emptyList(),
    val isLoading: Boolean = false
)
