package com.kareem.registrationsdk.presentation.screens.user_list.viewmodel

import androidx.lifecycle.viewModelScope
import com.kareem.registrationsdk.domain.usecase.GetAllUsersUseCase
import com.kareem.registrationsdk.presentation.core.base.BaseViewModel
import com.kareem.registrationsdk.presentation.core.base.UiEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class UsersListUiEffect : UiEffect() {
    data class ShowError(val message: String) : UsersListUiEffect()
}

@HiltViewModel
class UsersListViewModel @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase
) : BaseViewModel<UsersListState, UsersListEvent>(
    state = UsersListState(),
    event = UsersListEvent.Idle
) {

    override fun onEvent(event: UsersListEvent) {
        when (event) {
            UsersListEvent.LoadUsers -> {
                loadUsers()
            }

            UsersListEvent.Idle -> {}
        }
    }

    private fun loadUsers() {
        state = state.copy(isLoading = true)
        viewModelScope.launch (Dispatchers.IO + coroutineExceptionHandler) {
            getAllUsersUseCase().collectLatest { userList ->
                    state = state.copy(
                        users = userList,
                        isLoading = false
                    )
                }
        }
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        state = state.copy(isLoading = false)
        setEffect { UsersListUiEffect.ShowError(throwable.message ?: "Unknown error") }
    }

    init {
        onEvent(UsersListEvent.LoadUsers)
    }
}
