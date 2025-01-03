package com.kareem.registrationsdk.presentation.screens.register.second_step_screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.kareem.registrationsdk.presentation.core.navigation.LocalNavController
import com.kareem.registrationsdk.presentation.core.navigation.sharedViewModel
import com.kareem.registrationsdk.presentation.screens.register.shared_viewmodel.SharedRegistrationViewModel

@Composable
fun SecondStepScreen(
    navController: NavController = LocalNavController.current,
    sharedRegistrationViewModel: SharedRegistrationViewModel = navController.sharedViewModel()
) {

    Text(text = "Second Step Screen user : \n ${sharedRegistrationViewModel.state.userModel}")
}