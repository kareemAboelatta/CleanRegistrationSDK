package com.kareem.registrationsdk.presentation.screens.register.shared_viewmodel

import androidx.lifecycle.viewModelScope
import com.kareem.registrationsdk.domain.repository.RegisterRepository
import com.kareem.registrationsdk.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
        }
    }

    private fun saveUserData() {
        viewModelScope.launch {

        }
    }
}



