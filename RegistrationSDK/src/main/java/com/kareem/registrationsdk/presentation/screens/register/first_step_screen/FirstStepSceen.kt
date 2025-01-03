package com.kareem.registrationsdk.presentation.screens.register.first_step_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kareem.registrationsdk.presentation.core.components.DefaultTextField
import com.kareem.registrationsdk.presentation.core.components.DefaultTextFieldPassword
import com.kareem.registrationsdk.presentation.core.components.getErrorText
import com.kareem.registrationsdk.presentation.core.navigation.LocalNavController
import com.kareem.registrationsdk.presentation.core.navigation.Screens
import com.kareem.registrationsdk.presentation.core.navigation.sharedViewModel
import com.kareem.registrationsdk.presentation.screens.register.first_step_screen.viewmodel.FirstStepEvent
import com.kareem.registrationsdk.presentation.screens.register.first_step_screen.viewmodel.FirstStepUiEffect
import com.kareem.registrationsdk.presentation.screens.register.first_step_screen.viewmodel.FirstStepViewModel
import com.kareem.registrationsdk.presentation.screens.register.shared_viewmodel.SharedRegistrationEvent
import com.kareem.registrationsdk.presentation.screens.register.shared_viewmodel.SharedRegistrationViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstStepScreen(
    navController: NavController = LocalNavController.current,
    viewModel: FirstStepViewModel = hiltViewModel(),
    sharedViewModel: SharedRegistrationViewModel = navController.sharedViewModel(),
) {
    // Collect side effects (like navigation)
    LaunchedEffect(Unit) {
        viewModel.effectFlow.collectLatest { effect ->
            when (effect) {
                is FirstStepUiEffect.NavigateToSecondScreenToPickPhoto -> {
                    sharedViewModel.onEvent(
                        SharedRegistrationEvent.OnUserModelChanged(effect.userModel)
                    )
                    navController.navigate(Screens.RegistrationNavigation.SecondStepScreen)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Step 1") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(10.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Intro message
            Text(
                text = "Please enter your details below to continue",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Full Name Field
            DefaultTextField(
                keyboardType = androidx.compose.ui.text.input.KeyboardType.Text,
                hint = "Name",
                textInputHint = "e.g. John Doe (letters only)",
                textState = viewModel.state.name,
                error = getErrorText(
                    errorItem = viewModel.state.nameError,
                    fieldName = "Name",
                    invalidFormatMsg = "Name must contain letters only (no digits)."
                )
            ) { newValue ->
                viewModel.onEvent(FirstStepEvent.OnNameChanged(newValue))
            }

            Spacer(modifier = Modifier.height(5.dp))

            // Email Field
            DefaultTextField(
                keyboardType = androidx.compose.ui.text.input.KeyboardType.Email,
                hint = "Email",
                textInputHint = "e.g. example@domain.com",
                textState = viewModel.state.email,
                error = getErrorText(
                    errorItem = viewModel.state.emailError,
                    fieldName = "Email",
                    invalidFormatMsg = "Please enter a valid email address (e.g. user@domain.com)."
                )
            ) { newValue ->
                viewModel.onEvent(FirstStepEvent.OnEmailChanged(newValue))
            }

            Spacer(modifier = Modifier.height(5.dp))

            // Phone Field
            DefaultTextField(
                keyboardType = androidx.compose.ui.text.input.KeyboardType.Phone,
                hint = "Phone",
                textInputHint = "e.g. +123456789 or 123-456-7890",
                textState = viewModel.state.phone,
                error = getErrorText(
                    errorItem = viewModel.state.phoneError,
                    fieldName = "Phone",
                    invalidFormatMsg = "Please enter a valid phone number with at least 9 digits."
                )
            ) { newValue ->
                viewModel.onEvent(FirstStepEvent.OnPhoneChanged(newValue))
            }

            Spacer(modifier = Modifier.height(5.dp))

            // Password Field
            DefaultTextFieldPassword(
                hint = "Password",
                value = viewModel.state.password,
                errorText = getErrorText(
                    errorItem = viewModel.state.passwordError,
                    fieldName = "Password",
                    invalidFormatMsg = getPasswordRequirementsMsg()
                )
            ) { newValue ->
                viewModel.onEvent(FirstStepEvent.OnPasswordChanged(newValue))
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    viewModel.onEvent(FirstStepEvent.Submit)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Go to Second Step")
            }

            Button(
                onClick = {
                    navController.navigate(Screens.UsersListScreen)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(text = "Registered users")
            }
        }
    }
}


fun getPasswordRequirementsMsg(): String {
    return """
        Password must include at least:
         - One uppercase letter (A-Z)
         - One lowercase letter (a-z)
         - One symbol (e.g. !, @, #, $)
    """.trimIndent()
}
