package com.kareem.registrationsdk.presentation.core.navigation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.kareem.registrationsdk.presentation.screens.register.first_step_screen.FirstStepScreen
import com.kareem.registrationsdk.presentation.screens.register.second_step_screen.SecondStepScreen
import com.kareem.registrationsdk.presentation.screens.register.success_dialog.RegistrationSuccessDialog
import com.kareem.registrationsdk.presentation.screens.user_list.UsersListScreen


@Composable
fun Navigation(
    activity: ComponentActivity, // or pass a callback if you prefer

) {
    val navController = rememberNavController()

    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        NavHost(
            navController = navController,
            startDestination = Screens.RegistrationNavigation
        ) {

            // Nested navigation for registration flow:
            navigation<Screens.RegistrationNavigation>(
                startDestination = Screens.RegistrationNavigation.FirstStepScreen
            ) {
                composable<Screens.RegistrationNavigation.FirstStepScreen> {
                    FirstStepScreen()
                }

                composable<Screens.RegistrationNavigation.SecondStepScreen> {
                    SecondStepScreen()
                }
            }

            dialog<Screens.RegistrationSuccessDialog>(
                dialogProperties =  DialogProperties(
                    dismissOnClickOutside = false,
                )
            ) {
                RegistrationSuccessDialog(activity = activity)
            }


            composable<Screens.UsersListScreen> {
                UsersListScreen()
            }

        }

    }
}