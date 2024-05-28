package com.ignacioperez.whereami.ui.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.ignacioperez.whereami.R
import com.ignacioperez.whereami.navigation.Routes
import com.ignacioperez.whereami.ui.screens.tabs.pickuptabas.CardRuneTab
import com.ignacioperez.whereami.ui.screens.tabs.pickuptabas.HomeTab
import com.ignacioperez.whereami.ui.screens.tabs.pickuptabas.PillTab


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickupScreen(navController: NavController) {
    TabNavigator(
        HomeTab,
        tabDisposable = {
            TabDisposable(
                it,
                listOf(HomeTab, CardRuneTab, PillTab)
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.home_screen)) },
                    navigationIcon = {
                        var expanded by rememberSaveable {
                            mutableStateOf(false)
                        }

                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = stringResource(id = R.string.menu)
                            )
                        }
                        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                            DropdownMenuItem(
                                text = { Text(text = stringResource(id = R.string.home_screen)) },
                                onClick = { navController.navigate(Routes.HomeScreen.route) },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.home_screen_icon),
                                        contentDescription = stringResource(id = R.string.home_screen),
                                        modifier = Modifier.size(30.dp)
                                    )
                                })
                            DropdownMenuItem(
                                text = { Text(text = stringResource(id = R.string.characters)) },
                                onClick = { navController.navigate(Routes.CharactersScreen.route) },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.isaac_icon),
                                        contentDescription = stringResource(id = R.string.characters),
                                        modifier = Modifier.size(30.dp)
                                    )
                                })
                            DropdownMenuItem(
                                text = { Text(text = stringResource(id = R.string.trinkets)) },
                                onClick = { navController.navigate(Routes.TrinketsScreen.route) },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.trinket_curved_horn_icon),
                                        contentDescription = stringResource(id = R.string.trinkets),
                                        modifier = Modifier.size(30.dp)
                                    )
                                })
                            DropdownMenuItem(
                                text = { Text(text = stringResource(id = R.string.pickups)) },
                                onClick = { navController.navigate(Routes.PickupScreen.route) },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.rune_icon),
                                        contentDescription = stringResource(id = R.string.pickups),
                                        modifier = Modifier.size(30.dp)
                                    )
                                })

                        }
                    }
                )
            },
            bottomBar = {
                BottomNavigation {
                    val tabNavigator = LocalTabNavigator.current
                    BottomNavigationItem(
                        selected = tabNavigator.current.key == HomeTab.key,
                        label = { Text(HomeTab.options.title) },
                        icon = {
                            Icon(
                                painter = HomeTab.options.icon!!,
                                contentDescription = null
                            )
                        },
                        onClick = { tabNavigator.current = HomeTab }
                    )
                    BottomNavigationItem(
                        selected = tabNavigator.current.key == CardRuneTab.key,
                        label = { Text(CardRuneTab.options.title) },
                        icon = {
                            Icon(
                                painter = CardRuneTab.options.icon!!,
                                contentDescription = null
                            )
                        },
                        onClick = { tabNavigator.current = CardRuneTab }
                    )
                    BottomNavigationItem(
                        selected = tabNavigator.current.key == PillTab.key,
                        label = { Text(PillTab.options.title) },
                        icon = {
                            Icon(
                                painter = PillTab.options.icon!!,
                                contentDescription = null
                            )
                        },
                        onClick = { tabNavigator.current = PillTab }
                    )
                }
            },
            content = {
                Column(Modifier.padding(it)) {
                    CurrentTab()
                }
            }
        )
    }
}