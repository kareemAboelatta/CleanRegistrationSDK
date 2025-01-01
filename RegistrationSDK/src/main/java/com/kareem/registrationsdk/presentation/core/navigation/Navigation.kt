package com.kareem.registrationsdk.presentation.core.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.kareem.registrationsdk.presentation.screens.register.first_step_screen.FirstStepScreen
import com.kareem.registrationsdk.presentation.screens.register.second_step_screen.SecondStepScreen
import com.kareem.registrationsdk.presentation.screens.user_list.UsersListScreen


@Composable
fun Navigation() {
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


            composable<Screens.UsersListScreen> {
                UsersListScreen()
            }

        }

    }
}