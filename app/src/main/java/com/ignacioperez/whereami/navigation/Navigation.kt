package com.ignacioperez.whereami.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ignacioperez.whereami.ui.screens.HomeScreen
import com.ignacioperez.whereami.ui.screens.ListItems
import com.ignacioperez.whereami.ui.screens.Login
import com.ignacioperez.whereami.ui.screens.Register
import com.ignacioperez.whereami.viewmodel.ItemViewModel
import com.ignacioperez.whereami.viewmodel.SignInViewModel
import com.ignacioperez.whereami.viewmodel.UserViewModel

@Composable
fun Navigation(
    userViewModel: UserViewModel,
    signInViewModel: SignInViewModel,
    itemViewModel: ItemViewModel
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.LogInScreen.route) {
        composable(Routes.LogInScreen.route) {
            Login(navController, signInViewModel)
        }
        composable(Routes.RegisterScreen.route) {
            Register(navController = navController, userViewModel)
        }
        composable(Routes.ItemsScreen.route) {
            ListItems(itemViewModel = itemViewModel)
        }
        composable(Routes.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
    }
}