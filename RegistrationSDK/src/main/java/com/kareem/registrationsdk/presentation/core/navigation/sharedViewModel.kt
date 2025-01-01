package com.kareem.registrationsdk.presentation.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

/**
 * Retrieves a Hilt-managed [ViewModel] of type [T] that is shared across the nearest
 * navigation graph parent route. This allows multiple Composables that exist within the
 * same nested navigation graph to share the same [ViewModel] instance.
 *
 * ## Why Use This?
 * - **Shared State**: Often, multiple screens in a flow need to share state, such as form data or
 *   user selections. Using a shared [ViewModel] enables you to maintain a single source of truth
 *   within a specified navigation scope.
 * - **Scoped to Nested Navigation**: By scoping the [ViewModel] to the nearest parent navigation graph,
 *   the [ViewModel] will only live as long as that navigation graph is active. Once you navigate
 *   out of that nested graph, the shared [ViewModel] is cleared, freeing resources.
 *
 * ## How It Works
 * 1. Determine the parent navigation graph route of the current screen.
 * 2. Use [remember] to retrieve the [NavController.getBackStackEntry] for that route.
 * 3. Call [hiltViewModel] with the parent entry, retrieving the shared [ViewModel].
 * 4. If there is no parent route, fall back to a top-level [hiltViewModel].
 *
 * ## Usage
 * 1. Define a nested navigation graph for the screens you want to share state between.
 * 2. Inside those screens’ Composables, call `navController.sharedViewModel<T>()`
 *    to retrieve the same [ViewModel] instance.
 *
 * Example:
 * ```
 * navController.navigate("NestedGraph") {
 *   // Nested navigation destinations...
 * }
 *
 * // In a screen within 'NestedGraph':
 * @Composable
 * fun MyComposableScreen(navController: NavController) {
 *     val sharedVM: MySharedViewModel = navController.sharedViewModel()
 *     // Use sharedVM...
 * }
 * ```
 *
 * @return A shared [ViewModel] instance of type [T].
 */
@Composable
inline fun <reified T : ViewModel> NavController.sharedViewModel(): T {
    // Get the nearest parent's route. If it doesn't exist, return a top-level hiltViewModel().
    val navGraphRoute = this.currentBackStackEntry
        ?.destination
        ?.parent
        ?.route
        ?: return hiltViewModel()

    // Remember the parent back stack entry, so we only retrieve it once
    val parentEntry = remember(this) {
        this.getBackStackEntry(navGraphRoute)
    }

    // Return the ViewModel tied to the parent entry scope
    return hiltViewModel(parentEntry)
}
