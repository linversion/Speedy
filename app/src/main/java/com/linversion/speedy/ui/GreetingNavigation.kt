package com.linversion.speedy.ui

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable data object GreetingRoute

fun NavController.navigateToGreeting(navOptions: NavOptions) = navigate(route = GreetingRoute, navOptions)

fun NavGraphBuilder.greetingScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    composable<GreetingRoute> {
        Greeting(
            name = "Hello World!",
            onShowSnackbar = onShowSnackbar
        )
    }
}