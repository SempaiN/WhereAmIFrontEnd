package com.ignacioperez.whereami.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ignacioperez.whereami.R
import com.ignacioperez.whereami.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.home_screen)) },
                navigationIcon = {
                    var expanded by rememberSaveable {
                        mutableStateOf(false)
                    }
                    var subExpanded by rememberSaveable {
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
                            })

                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.about),
                style = MaterialTheme.typography.headlineMedium,
            )
            Divider(modifier = Modifier.fillMaxWidth(), thickness = 4.dp)
            Text(
                text = stringResource(R.string.gameplay),
                style = MaterialTheme.typography.titleLarge
            )
            Text(text = stringResource(R.string.gameplay_text))
            Spacer(modifier = Modifier.size(18.dp))
            Text(
                text = stringResource(R.string.important_information),
                style = MaterialTheme.typography.titleLarge
            )
            Column(
                modifier = Modifier.padding(start = 7.dp)
            ) {
                Information(R.string.information_character)
                Information(R.string.information_item)
                Information(R.string.home_screen)
                Spacer(modifier = Modifier.size(width = 0.dp, height = 10.dp))
                Button(
                    onClick = { navController.navigate(Routes.MoreInformationScreen.route) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.more_information))
                }

            }
        }
    }
}

@Composable
fun Information(idString: Int) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),

        ) {
        Text(
            text = "\u2022",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(text = stringResource(idString))
    }
}