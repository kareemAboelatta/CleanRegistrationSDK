package com.kareem.registrationsdk.presentation.screens.register.shared_viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.kareem.registrationsdk.domain.repository.RegisterRepository
import com.kareem.registrationsdk.domain.usecase.RegisterUseCase
import com.kareem.registrationsdk.presentation.core.base.BaseViewModel
import com.kareem.registrationsdk.presentation.core.base.UiEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
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
    private val registerRepository: RegisterRepository
) :
    BaseViewModel<SharedRegistrationState, SharedRegistrationEvent>(
        SharedRegistrationState(),
        SharedRegistrationEvent.Idle
    ) {


    override fun onEvent(event: SharedRegistrationEvent) {
        when (event) {
            SharedRegistrationEvent.Idle -> {}
            is SharedRegistrationEvent.OnUserModelChanged -> {
                state = state.copy(userModel = event.userModel)
            }

            SharedRegistrationEvent.SaveUserData -> {
                saveUserData()
            }

            is SharedRegistrationEvent.OnImageChangedChanged -> {
                state = state.copy(
                    userModel = state.userModel?.copy(userImage = event.image)
                )
            }
        }
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("TAG", "SharedRegistrationViewModel:  $throwable");
        setEffect { RegistrationUiEffect.ShowError(throwable.message.toString()) }
    }

    private fun saveUserData() {
        val customCoroutineContext = Dispatchers.IO + coroutineExceptionHandler + NonCancellable
        CoroutineScope(
            customCoroutineContext
        ).launch {
//            state.userModel?.let { registerRepository.insertUser(it) }
            Log.d("TAG", "SharedRegistrationViewModel: saveUserData: ${state.userModel}");
        }
    }
}



