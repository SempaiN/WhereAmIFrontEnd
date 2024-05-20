package com.ignacioperez.whereami.navigation

sealed class Routes(val route: String) {
    object LogInScreen : Routes("LogInScreen")
    object RegisterScreen : Routes("RegisterScreen")
    object HomeScreen : Routes("HomeScreen")
    object ItemsScreen: Routes("ItemsScreen")
}