package com.ignacioperez.whereami.ui.screens.pickups

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ignacioperez.whereami.R
import com.ignacioperez.whereami.models.ListPills
import com.ignacioperez.whereami.models.User
import com.ignacioperez.whereami.mycomposables.PillCard
import com.ignacioperez.whereami.navigation.Routes
import com.ignacioperez.whereami.viewmodel.PillViewModel
import com.ignacioperez.whereami.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListPills(
    pillViewModel: PillViewModel,
    userViewModel: UserViewModel,
    navController: NavController
) {

    pillViewModel.getAllPills()
    pillViewModel.getPillsNegative()
    pillViewModel.getPillsPositive()
    pillViewModel.getPillsNeutral()
    pillViewModel.getUnlockablePills()
    val pillList: ListPills by pillViewModel.allPills.observeAsState(
        ListPills()
    )
    val unlockablePills: ListPills by pillViewModel.pillsUnlockable.observeAsState(
        ListPills()
    )
    val negativePills: ListPills by pillViewModel.pillsNegative.observeAsState(
        ListPills()
    )
    val positivePills: ListPills by pillViewModel.pillsPositive.observeAsState(
        ListPills()
    )
    val neutralPills: ListPills by pillViewModel.allPills.observeAsState(
        ListPills()
    )
    var showOnlyFavorite by rememberSaveable { mutableStateOf(false) }
    var showOnlyNegative by rememberSaveable { mutableStateOf(false) }
    var showOnlyNeutral by rememberSaveable { mutableStateOf(false) }
    var showOnlyPositive by rememberSaveable { mutableStateOf(false) }
    var showOnlyUnlockable by rememberSaveable { mutableStateOf(false) }
    val favoritePillsList: ListPills by userViewModel.favoritePillsList.observeAsState(ListPills())
    val user: User by userViewModel.user.observeAsState(User())
    userViewModel.getFavoritePills(user)
    val showDialog: Boolean by pillViewModel.showPillDetails.observeAsState(
        false
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.home_screen)) },
                navigationIcon = {
                    var expanded by rememberSaveable { mutableStateOf(false) }

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
                            }
                        )
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
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = stringResource(id = R.string.trinkets)) },
                            onClick = { navController.navigate(Routes.TrinketsScreen.route) },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.trinket_curved_horn_icon),
                                    contentDescription = stringResource(id = R.string.trinkets),
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        )
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
                    onClick = { navController.navigate(Routes.PickupScreen.route) },
                    icon = {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = stringResource(R.string.home_pickups)
                        )
                    },
                    label = {
                        Text(stringResource(R.string.home_pickups))
                    }
                )
                BottomNavigationItem(
                    selected = false,
                    onClick = { navController.navigate(Routes.ListCardRunes.route) },
                    icon = {
                        Icon(
                            painterResource(R.drawable.facecard),
                            contentDescription = stringResource(R.string.ListCards)
                        )
                    },
                    label = { Text(stringResource(R.string.ListCards)) }
                )
            }
        }
    ) {
        Column(Modifier.padding(it)) {
            Column(Modifier.padding(8.dp)) {
                Row(){
                    FilterChip(
                        selected = showOnlyFavorite,
                        onClick = {
                            showOnlyFavorite = !showOnlyFavorite
                            showOnlyNegative = false
                            showOnlyNeutral = false
                            showOnlyPositive = false
                            showOnlyUnlockable = false
                        },
                        label = { Text(stringResource(R.string.favorite_items)) }
                    )
                    FilterChip(
                        selected = showOnlyNegative,
                        onClick = {
                            showOnlyFavorite = false
                            showOnlyNegative = !showOnlyNegative
                            showOnlyNeutral = false
                            showOnlyPositive = false
                            showOnlyUnlockable = false
                        },
                        label = { Text(stringResource(R.string.negative_pills)) }
                    )
                }
                Row(){
                    FilterChip(
                        selected = showOnlyNeutral,
                        onClick = {
                            showOnlyFavorite = false
                            showOnlyNegative = false
                            showOnlyNeutral = !showOnlyNeutral
                            showOnlyPositive = false
                            showOnlyUnlockable = false
                        },
                        label = { Text(stringResource(R.string.neutral_pills)) }
                    )
                    FilterChip(
                        selected = showOnlyUnlockable,
                        onClick = {
                            showOnlyFavorite = false
                            showOnlyNegative = false
                            showOnlyNeutral = false
                            showOnlyPositive = false
                            showOnlyUnlockable = !showOnlyUnlockable
                        },
                        label = { Text(stringResource(R.string.unlockable_pills)) }
                    )
                }

            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                items(
                    when {
                        showOnlyFavorite -> favoritePillsList
                        showOnlyNegative -> negativePills
                        showOnlyNeutral -> neutralPills
                        showOnlyPositive -> positivePills
                        showOnlyFavorite -> favoritePillsList
                        else -> pillList
                    }
                ) { pill ->
                    if (favoritePillsList.contains(pill)) {
                        PillCard(pill, pillViewModel, true)
                    } else {
                        PillCard(pill, pillViewModel, false)
                    }
                }
            }
        }

            if (showDialog) {
                PillDetails(pillViewModel, userViewModel)
            }
        }

    }


