package com.ignacioperez.whereami.ui.screens.pickups


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigationItem


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Info

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.ignacioperez.whereami.R
import com.ignacioperez.whereami.navigation.Routes
import com.ignacioperez.whereami.ui.screens.home.Information


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickupScreen(navController: NavController) {
    var navigationSelectedItem by remember {
        mutableStateOf(0)
    }

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
                            text = { Text(text = stringResource(id = R.string.items)) },
                            onClick = { navController.navigate(Routes.ItemsScreen.route) },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.godhead_icon),
                                    contentDescription = stringResource(id = R.string.items),
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
                            }, enabled = false
                        )
                        DropdownMenuItem(
                            text = { Text(text = stringResource(id = R.string.more_information)) },
                            onClick = { navController.navigate(Routes.MoreInformationScreen.route) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Info,
                                    contentDescription = stringResource(id = R.string.more_information),
                                    modifier = Modifier.size(30.dp)
                                )
                            })

                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                BottomNavigationItem(
                    selected = false,
                    onClick = { navController.navigate(Routes.ListCardRunes.route) },
                    icon = {
                        Icon(
                            painterResource(R.drawable.facecard),
                            contentDescription = stringResource(R.string.card_lookLike)
                        )
                    },
                    label = {

                        Text(
                            stringResource(R.string.ListCards)
                        )
                    }
                )
                BottomNavigationItem(
                    selected = false,
                    onClick = { navController.navigate(Routes.ListPills.route) },
                    icon = {
                        Icon(
                            painterResource(R.drawable.pill_black_white),
                            contentDescription = stringResource(R.string.listPills)
                        )
                    },
                    label = { Text(stringResource(R.string.listPills)) }

                )


            }
        }

    ) {
        Column(
            Modifier
                .padding(it)
                .padding(6.dp)
        ) {
            Information(R.string.pickups_info)
            Information(R.string.chests_info)
            Information(R.string.pills_info)
            Information(R.string.cards_rune_info)

            Column(verticalArrangement = Arrangement.Center) {
                Information(R.string.rune_lookLike)
                Spacer(Modifier.size(width = 15.dp, height = 0.dp))

                Image(
                    painter = painterResource(R.drawable.rune2),
                    contentDescription = "",
                    modifier = Modifier.size(40.dp)
                )
            }
            Column(verticalArrangement = Arrangement.Center) {
                Information(R.string.inverseCardlookLike)
                Spacer(Modifier.size(width = 15.dp, height = 0.dp))

                Image(
                    painter = painterResource(R.drawable.reversetarotcard),
                    contentDescription = "",
                    modifier = Modifier.size(40.dp)
                )
            }
            Column(verticalArrangement = Arrangement.Center) {
                Information(R.string.card_lookLike)
                Spacer(Modifier.size(width = 15.dp, height = 0.dp))
                Image(
                    painter = painterResource(R.drawable.tarotcard),
                    contentDescription = "",
                    modifier = Modifier.size(40.dp)
                )
            }

        }
    }
}


