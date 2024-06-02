package com.ignacioperez.whereami.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ignacioperez.whereami.ui.screens.CharacterDetails
import com.ignacioperez.whereami.ui.screens.CreateCharacterScreen
import com.ignacioperez.whereami.ui.screens.HomeScreen
import com.ignacioperez.whereami.ui.screens.ItemDetailsScreen
import com.ignacioperez.whereami.ui.screens.ListCharacters
import com.ignacioperez.whereami.ui.screens.ListItems
import com.ignacioperez.whereami.ui.screens.ListTrinkets
import com.ignacioperez.whereami.ui.screens.Login
import com.ignacioperez.whereami.ui.screens.MoreInformationScreen
import com.ignacioperez.whereami.ui.screens.PickupScreen
import com.ignacioperez.whereami.ui.screens.Pickups
import com.ignacioperez.whereami.ui.screens.Register
import com.ignacioperez.whereami.ui.screens.SelectItemScreen
import com.ignacioperez.whereami.ui.screens.SelectCardRunePillScreen
import com.ignacioperez.whereami.ui.screens.SelectTrinketScreen
import com.ignacioperez.whereami.ui.screens.TrinketDetailsScreen
import com.ignacioperez.whereami.viewmodel.CardRuneViewModel
import com.ignacioperez.whereami.viewmodel.CharacterViewModel
import com.ignacioperez.whereami.viewmodel.ItemViewModel
import com.ignacioperez.whereami.viewmodel.NewCharacterViewModel
import com.ignacioperez.whereami.viewmodel.PillViewModel
import com.ignacioperez.whereami.viewmodel.SignInViewModel
import com.ignacioperez.whereami.viewmodel.TrinketViewModel
import com.ignacioperez.whereami.viewmodel.UserViewModel

@Composable
fun getNavController(): NavHostController {
    var navController = rememberNavController()
    return navController
}

@Composable
fun Navigation(
    userViewModel: UserViewModel,
    signInViewModel: SignInViewModel,
    itemViewModel: ItemViewModel,
    characterViewModel: CharacterViewModel,
    trinketViewModel: TrinketViewModel,
    cardRuneViewModel: CardRuneViewModel,
    newCharacterViewModel: NewCharacterViewModel,
    pillViewModel: PillViewModel
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
            ListItems(itemViewModel = itemViewModel, navController = navController)
        }
        composable(Routes.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
        composable(Routes.CharactersScreen.route) {
            ListCharacters(characterViewModel = characterViewModel, navController, signInViewModel)
        }
        composable(Routes.TrinketsScreen.route) {
            ListTrinkets(trinketViewModel, navController)
        }
        composable(Routes.PickupScreen.route) {
            Pickups()
        }
        composable(Routes.MoreInformationScreen.route) {
            MoreInformationScreen(navController = navController)
        }
        composable(Routes.ItemDetailsScreen.route) {
            ItemDetailsScreen(navController = navController, itemViewModel)
        }
        composable(Routes.CharacterDetailsScreen.route) {
            CharacterDetails(navController, characterViewModel, itemViewModel)
        }
        composable(Routes.TrinketDetailsScreen.route) {
            TrinketDetailsScreen(navController, trinketViewModel)
        }
        composable(Routes.PickupScreen.route) {
            PickupScreen(navController)
        }
        composable(Routes.CreateCharacterScreen.route) {
            CreateCharacterScreen(navController, newCharacterViewModel)
        }
        composable(Routes.SelectItemScreen.route) {
            SelectItemScreen(newCharacterViewModel, itemViewModel, navController)
        }
        composable(Routes.SelectCardRunePillScreen.route) {
            SelectCardRunePillScreen(
                cardRuneViewModel,
                pillViewModel,
                newCharacterViewModel,
                navController
            )
        }
        composable(Routes.SelectTrinketScreen.route) {
            SelectTrinketScreen()
        }
    }
}
