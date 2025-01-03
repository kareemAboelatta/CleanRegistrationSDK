package com.kareem.registrationsdk.presentation.screens.user_list.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.kareem.registrationsdk.domain.usecase.GetAllUsersUseCase
import com.kareem.registrationsdk.presentation.core.base.BaseViewModel
import com.kareem.registrationsdk.presentation.core.base.UiEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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

        viewModelScope.launch {
            getAllUsersUseCase()
                .catch { throwable ->
                    setEffect { UsersListUiEffect.ShowError(throwable.message ?: "Unknown error") }
                }
                .collectLatest { userList ->
                    Log.d("TAG", "UsersListViewModel: loadUsers: userList = $userList");
                    state = state.copy(
                        users = userList,
                        isLoading = false
                    )
                }
        }
    }
    
    init {
        Log.d("TAG", "UsersListViewModel: init: ");
        onEvent(UsersListEvent.LoadUsers)
    }

}
