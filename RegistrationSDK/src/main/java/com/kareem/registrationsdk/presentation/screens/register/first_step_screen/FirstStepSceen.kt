package com.kareem.registrationsdk.presentation.screens.register.first_step_screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kareem.registrationsdk.presentation.core.navigation.LocalNavController
import com.kareem.registrationsdk.presentation.core.navigation.Screens
import com.kareem.registrationsdk.presentation.core.navigation.sharedViewModel
import com.kareem.registrationsdk.presentation.screens.register.first_step_screen.viewmodel.FirstStepUiEffect
import com.kareem.registrationsdk.presentation.screens.register.first_step_screen.viewmodel.FirstStepViewModel
import com.kareem.registrationsdk.presentation.screens.register.shared_viewmodel.SharedRegistrationEvent
import com.kareem.registrationsdk.presentation.screens.register.shared_viewmodel.SharedRegistrationViewModel
import kotlinx.coroutines.flow.collectLatest


@Composable
fun FirstStepScreen(
    navController: NavController = LocalNavController.current,
    viewModel: FirstStepViewModel = hiltViewModel(),
    sharedViewModel: SharedRegistrationViewModel = navController.sharedViewModel(),
) {

    LaunchedEffect(key1 = true) {
        viewModel.effectFlow.collectLatest { effect ->
            when (effect) {
                //save user data in shared view model between register screens
                is FirstStepUiEffect.NavigateToSecondScreenToPickPhoto -> {
                    sharedViewModel.onEvent(
                        SharedRegistrationEvent.OnUserModelChanged(
                            effect.userModel
                        )
                    )
                    navController.navigate(Screens.RegistrationNavigation.SecondStepScreen)
                }
            }

        }
    }



    Text(text = "First Step Screen")

}