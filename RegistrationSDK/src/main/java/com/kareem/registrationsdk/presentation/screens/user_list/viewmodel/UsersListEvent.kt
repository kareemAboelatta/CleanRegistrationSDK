package com.kareem.registrationsdk.presentation.screens.user_list.viewmodel

sealed class UsersListEvent {
    data object Idle : UsersListEvent()
    data object LoadUsers : UsersListEvent()
}
