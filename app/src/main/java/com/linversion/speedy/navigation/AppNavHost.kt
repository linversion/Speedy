package com.linversion.speedy.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.linversion.speedy.ui.AppState
import com.linversion.speedy.ui.GreetingRoute
import com.linversion.speedy.ui.greetingScreen

@Composable
fun AppNavHost(
    appState: AppState,
    onShowSnackBar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = GreetingRoute,
        modifier = modifier
    ) {
        greetingScreen(onShowSnackbar = onShowSnackBar)
    }
}