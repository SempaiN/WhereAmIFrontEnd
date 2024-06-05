package com.ignacioperez.whereami.navigation

import com.ignacioperez.whereami.ui.screens.create_character.SelectStatsScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ignacioperez.whereami.ui.screens.characters.CharacterDetails
import com.ignacioperez.whereami.ui.screens.create_character.CreateCharacterScreen
import com.ignacioperez.whereami.ui.screens.home.HomeScreen
import com.ignacioperez.whereami.ui.screens.Items.ItemDetailsScreen
import com.ignacioperez.whereami.ui.screens.pickups.ListCardRunes
import com.ignacioperez.whereami.ui.screens.characters.ListCharacters
import com.ignacioperez.whereami.ui.screens.Items.ListItems
import com.ignacioperez.whereami.ui.screens.pickups.ListPills
import com.ignacioperez.whereami.ui.screens.trinkets.ListTrinkets
import com.ignacioperez.whereami.ui.screens.loginsignin.Login
import com.ignacioperez.whereami.ui.screens.home.MoreInformationScreen
import com.ignacioperez.whereami.ui.screens.pickups.PickupScreen
import com.ignacioperez.whereami.ui.screens.loginsignin.Register
import com.ignacioperez.whereami.ui.screens.create_character.SelectItemScreen
import com.ignacioperez.whereami.ui.screens.create_character.SelectCardRunePillScreen
import com.ignacioperez.whereami.ui.screens.create_character.SelectTrinketScreen
import com.ignacioperez.whereami.ui.screens.trinkets.TrinketDetailsScreen
import com.ignacioperez.whereami.viewmodel.CardRuneViewModel
import com.ignacioperez.whereami.viewmodel.CharacterViewModel
import com.ignacioperez.whereami.viewmodel.ItemViewModel
import com.ignacioperez.whereami.viewmodel.NewCharacterViewModel
import com.ignacioperez.whereami.viewmodel.PillViewModel
import com.ignacioperez.whereami.viewmodel.UserViewModel
import com.ignacioperez.whereami.viewmodel.TrinketViewModel
import com.ignacioperez.whereami.viewmodel.RegisterViewModel

@Composable
fun getNavController(): NavHostController {
    var navController = rememberNavController()
    return navController
}

@Composable
fun Navigation(
    registerViewModel: RegisterViewModel,
    userViewModel: UserViewModel,
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
            Login(navController, userViewModel)
        }
        composable(Routes.RegisterScreen.route) {
            Register(navController = navController, registerViewModel)
        }
        composable(Routes.ItemsScreen.route) {
            ListItems(itemViewModel = itemViewModel, navController = navController, userViewModel)
        }
        composable(Routes.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
        composable(Routes.CharactersScreen.route) {
            ListCharacters(characterViewModel = characterViewModel, navController, userViewModel)
        }
        composable(Routes.TrinketsScreen.route) {
            ListTrinkets(trinketViewModel, navController)
        }
        composable(Routes.MoreInformationScreen.route) {
            MoreInformationScreen(navController = navController)
        }
        composable(Routes.ItemDetailsScreen.route) {
            ItemDetailsScreen(navController = navController, itemViewModel, userViewModel)
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
                navController,
                userViewModel
            )
        }
        composable(Routes.SelectTrinketScreen.route) {
            SelectTrinketScreen(newCharacterViewModel, trinketViewModel, navController)
        }
        composable(Routes.SelectStatsScreen.route) {
            SelectStatsScreen(
                newCharacterViewModel,
                itemViewModel,
                cardRuneViewModel,
                pillViewModel,
                trinketViewModel
            )
        }
        composable(Routes.ListCardRunes.route) {
            ListCardRunes(cardRuneViewModel, userViewModel, navController)
        }
        composable(Routes.ListPills.route) {
            ListPills(pillViewModel, userViewModel, navController)
        }
    }
}
