package com.ignacioperez.whereami.ui.screens.Items

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.BatteryFull
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ignacioperez.whereami.R
import com.ignacioperez.whereami.models.Item
import com.ignacioperez.whereami.models.User
import com.ignacioperez.whereami.navigation.Routes
import com.ignacioperez.whereami.viewmodel.ItemViewModel
import com.ignacioperez.whereami.viewmodel.UserViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListItems(
    itemViewModel: ItemViewModel,
    navController: NavController,
    userViewModel: UserViewModel
) {
    val user: User by userViewModel.user.observeAsState(
        initial = User()
    )
    itemViewModel.getAllItems()
    itemViewModel.getActiveItems()
    itemViewModel.getUnlockableItems()
    userViewModel.getFavoriteItems(user)
    val itemList: List<Item> by itemViewModel.allItems.observeAsState(initial = emptyList())
    val unlockableList: List<Item> by itemViewModel.unlockalbeItems.observeAsState(initial = emptyList())
    val activeList: List<Item> by itemViewModel.itemsActives.observeAsState(initial = emptyList())
    val favoriteItems: List<Item> by userViewModel.favoriteItemsList.observeAsState(emptyList())

    var showOnlyFavoriteItems: Boolean by rememberSaveable { mutableStateOf(false) }
    var showOnlyActiveItems: Boolean by rememberSaveable { mutableStateOf(false) }
    var showOnlyUnlockablesItems: Boolean by rememberSaveable { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.list_items)) },
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
                            }, enabled = false
                        )
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
                            },
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
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            Row(Modifier.padding(8.dp)) {
                FilterChip(
                    selected = showOnlyActiveItems,
                    onClick = {
                        showOnlyActiveItems = !showOnlyActiveItems
                        showOnlyUnlockablesItems = false
                        showOnlyFavoriteItems = false
                    },
                    label = { Text(stringResource(R.string.active_items)) },

                )
                FilterChip(
                    selected = showOnlyUnlockablesItems,
                    onClick = {
                        showOnlyActiveItems = false
                        showOnlyUnlockablesItems = !showOnlyUnlockablesItems
                        showOnlyFavoriteItems = false
                    },
                    label = { Text(stringResource(R.string.unlockable_items)) }
                )
                FilterChip(
                    selected = showOnlyFavoriteItems,
                    onClick = {
                        showOnlyActiveItems = false
                        showOnlyUnlockablesItems = false
                        showOnlyFavoriteItems = !showOnlyFavoriteItems
                    },
                    label = { Text(stringResource(R.string.favorite_items)) }
                )
            }
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(
                    when {
                        showOnlyActiveItems -> activeList
                        showOnlyUnlockablesItems -> unlockableList
                        showOnlyFavoriteItems -> favoriteItems
                        else -> itemList
                    }
                ) { item ->
                    ItemCard(
                        item = item,
                        itemViewModel = itemViewModel,
                        navController = navController,
                        favorite = favoriteItems.contains(item)
                    )
                }
            }
        }
    }
}


@Composable
fun ItemCard(
    item: Item,
    itemViewModel: ItemViewModel,
    navController: NavController,
    favorite: Boolean
) {
    val rainbowColorsBrush = remember {
        Brush.sweepGradient(
            listOf(
                Color(0xFF9575CD),
                Color(0xFFBA68C8),
                Color(0xFFE57373),
                Color(0xFFFFB74D),
                Color(0xFFFFF176),
                Color(0xFFAED581),
                Color(0xFF4DD0E1),
                Color(0xFF9575CD)
            )
        )
    }
    val borderWidth = 1.5.dp
    OutlinedCard(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable {
                itemViewModel.onItemClicked(item = item)
                navController.navigate(route = Routes.ItemDetailsScreen.route)
            }
            .border(
                if (favorite) {
                    BorderStroke(borderWidth, rainbowColorsBrush)
                } else {
                    BorderStroke(1.5.dp, Color.Black)
                }, shape = CardDefaults.outlinedShape
            )

    ) {
        ListItem(
            headlineContent = {
                Text(text = item.name, style = MaterialTheme.typography.titleLarge)
            },
            leadingContent = {
                AsyncImage(
                    model = item.imageUrl,
                    placeholder = painterResource(id = R.drawable.godhead_icon),
                    error = painterResource(id = R.drawable.godhead_icon),
                    contentDescription = item.name,
                    modifier = Modifier.size(70.dp)
                )

            }
        )

    }
}