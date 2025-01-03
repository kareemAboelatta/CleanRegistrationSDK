package com.kareem.registrationsdk.presentation.core.navigation

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController

val LocalNavController = staticCompositionLocalOf<NavController> {
    error("No NavController provided")
}