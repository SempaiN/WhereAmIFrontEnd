package com.ignacioperez.whereami.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ignacioperez.whereami.R
import com.ignacioperez.whereami.models.CharacterResponse
import com.ignacioperez.whereami.models.User
import com.ignacioperez.whereami.navigation.Routes
import com.ignacioperez.whereami.viewmodel.CharacterViewModel
import com.ignacioperez.whereami.viewmodel.SignInViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListCharacters(
    characterViewModel: CharacterViewModel,
    navController: NavController,
    signInViewModel: SignInViewModel
) {
    val user: User by signInViewModel.user.observeAsState(
        initial = User()
    )
    val customCharacters: List<CharacterResponse> by signInViewModel.charactersCustom.observeAsState(
        initial = emptyList()
    )
    characterViewModel.loadCharacters()
    signInViewModel.getCharactersCustom(user)
    val characterList: List<CharacterResponse> by characterViewModel.defaultCharacters.observeAsState(
        initial = emptyList()
    )

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
        }
    ) {
        LazyVerticalGrid(
            modifier = Modifier.padding(it),
            columns = GridCells.Adaptive(100.dp),
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center

        ) {
            items(
                characterList + customCharacters
            ) { character ->
                CharacterCard(character)
            }
        }
    }
}

@Composable
fun CharacterCard(character: CharacterResponse) {
    OutlinedCard(
        modifier = Modifier.padding(vertical = 7.dp, horizontal = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(start = 8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                character.imageUrl,
                contentDescription = character.name,
                modifier = if (character.custom) Modifier.size(120.dp) else Modifier.size(100.dp)
            )
            Text(
                text = character.name,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}