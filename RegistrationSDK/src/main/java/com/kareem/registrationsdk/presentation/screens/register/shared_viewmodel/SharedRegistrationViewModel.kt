package com.kareem.registrationsdk.presentation.screens.register.shared_viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.kareem.registrationsdk.domain.usecase.RegisterUseCase
import com.kareem.registrationsdk.presentation.core.base.BaseViewModel
import com.kareem.registrationsdk.presentation.core.base.UiEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * [SharedRegistrationViewModel]
 *
 * This ViewModel holds user registration data (name, phone, email, password)
 * across multiple registration screens.
 *
 * - If all validations pass in the first screen, we navigate to the second screen
 *   to capture or pick an image.
 * - Once the user completes photo selection, we'll have a complete [UserModel]
 *   that can then be saved or submitted to the repository (e.g., local DB).
 *
 * This approach allows data to persist between navigation steps without
 * forcing us to pass arguments through NavController or Bundles.
 */

sealed class RegistrationUiEffect : UiEffect() {
    data object UserDataSaved : RegistrationUiEffect()  // signals that all steps are complete
    data class ShowError(val message: String) : RegistrationUiEffect()
}



@HiltViewModel
class SharedRegistrationViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : BaseViewModel<SharedRegistrationState, SharedRegistrationEvent>(
    SharedRegistrationState(),
    SharedRegistrationEvent.Idle
) {

    override fun onEvent(event: SharedRegistrationEvent) {
        when (event) {
            SharedRegistrationEvent.Idle -> {}
            is SharedRegistrationEvent.OnUserModelChanged -> {
                state = state.copy(userModel = event.userModel)
            }
            is SharedRegistrationEvent.OnImageChanged -> {
                state = state.copy(
                    userModel = state.userModel?.copy(userImage = event.image)
                )
            }
            SharedRegistrationEvent.SaveUserData -> {
                saveUserData()
            }
        }
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("SharedRegViewModel", "Error saving user data: ", throwable)
        setEffect { RegistrationUiEffect.ShowError("Error saving user data:" + throwable.message.orEmpty()) }
    }

    private fun saveUserData() {
        // Since you want to do DB operations, you can use viewModelScope with Dispatchers.IO
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val user = state.userModel
            if (user == null) {
                Log.e("SharedRegViewModel", "No user data to save.")
                return@launch
            }

            val rowId = registerUseCase(user)

            if (rowId > 0) {
                // insertion successful
                Log.d("SharedRegViewModel", "User inserted with id = $rowId")
                setEffect { RegistrationUiEffect.UserDataSaved }
                // you can also emit a UI effect or navigate
            } else {
                // insertion failed or replaced an existing row with the same ID
                setEffect { RegistrationUiEffect.ShowError("User insertion failed or replaced, rowId = $rowId") }

            }
        }
    }
}



