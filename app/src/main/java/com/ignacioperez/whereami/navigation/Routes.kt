package com.ignacioperez.whereami.navigation

sealed class Routes(val route: String) {
    object LogInScreen : Routes("LogInScreen")
    object RegisterScreen : Routes("RegisterScreen")
    object HomeScreen : Routes("HomeScreen")
    object ItemsScreen : Routes("ItemsScreen")
    object CharactersScreen : Routes("CharactersScreen")
    object TrinketsScreen : Routes("TrinketsScreen")
    object PickupScreen : Routes("PickupScreen")
    object MoreInformationScreen: Routes("MoreInformationScreen")
    object ItemDetailsScreen: Routes("ItemDetailsScreen")
    object CharacterDetailsScreen: Routes("CharacterDetailsScreen")
    object TrinketDetailsScreen: Routes("TrinketDetailsScreen")
    object CreateCharacterScreen: Routes("CreateCharacterScreen")
    object SelectItemScreen: Routes("SelectItemScreen")
    object SelectCardRunePillScreen: Routes("SelectCardRunePillScreen")
    object SelectTrinketScreen: Routes("SelectTrinketScreen")
    object SelectStatsScreen: Routes("SelectStatsScreen")
}