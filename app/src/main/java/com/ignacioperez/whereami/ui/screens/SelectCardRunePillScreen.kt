package com.ignacioperez.whereami.ui.screens

import CardRuneDetails
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material3.AlertDialog

import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.ignacioperez.whereami.R
import com.ignacioperez.whereami.navigation.Routes
import com.ignacioperez.whereami.viewmodel.CardRuneViewModel
import com.ignacioperez.whereami.viewmodel.NewCharacterViewModel
import com.ignacioperez.whereami.viewmodel.PillViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectCardRunePillScreen(
    cardRuneViewModel: CardRuneViewModel,
    pillViewModel: PillViewModel,
    newCharacterViewModel: NewCharacterViewModel,
    navController: NavHostController
) {
    var selectPill by rememberSaveable { mutableStateOf(false) }
    var selectCardRune by rememberSaveable { mutableStateOf(false) }
    val listAllCardRune by cardRuneViewModel.allCardsRunes.observeAsState(emptyList())
    val listAllPills by pillViewModel.allPills.observeAsState(emptyList())
    var search by rememberSaveable { mutableStateOf("") }
    var showAlertDialogSelectObject by rememberSaveable { mutableStateOf(false) }
    val cardRuneNewCharacter by newCharacterViewModel.cardRuneNewCharacter.observeAsState(initial = null)
    val pillNewCharacter by newCharacterViewModel.pillNewCharacter.observeAsState(initial = null)
    var showDetails by rememberSaveable {
        mutableStateOf(false)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.item_details)) }
            )
        },
        floatingActionButton = {
            if ((selectCardRune || selectPill) && (pillNewCharacter != null || cardRuneNewCharacter != null)) {
                FloatingActionButton(onClick = {
                    navController.navigate(Routes.SelectTrinketScreen.route)
                }) {
                    Icon(Icons.Filled.SkipNext, stringResource(R.string.select_trinket_Screen))
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            if (!selectPill && !selectCardRune) {
                Text(
                    text = stringResource(R.string.pill_or_cardRune),
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Button(
                    onClick = {
                        selectCardRune = true
                        cardRuneViewModel.getAllCardsRunes()
                    },
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(text = stringResource(R.string.click_cardRune))
                }

                Button(
                    onClick = {
                        selectPill = true
                        pillViewModel.getAllPills()
                    },
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(text = stringResource(R.string.click_pill))
                }
                Button(
                    onClick = {
                        navController.navigate(Routes.SelectTrinketScreen.route)
                    },
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(text = stringResource(R.string.consumible_empty))
                }
            }
            if (selectPill) {
                Text(
                    text = stringResource(R.string.select_Pill),
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                if (pillNewCharacter == null) {
                    Button(onClick = {
                        showAlertDialogSelectObject = !showAlertDialogSelectObject
                    }) {
                        Text(stringResource(R.string.select_Object_list))
                    }
                } else {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            stringResource(R.string.start_object),
                            style = MaterialTheme.typography.h6
                        )
                        TextButton(onClick = {
                            pillViewModel.onPillClicked(pillNewCharacter!!)
                            showDetails = !showDetails
                        }) {
                            Text(
                                text = pillNewCharacter!!.name,
                                style = MaterialTheme.typography.h6
                            )
                        }
                    }
                    Button(onClick = { newCharacterViewModel.setPillCharacterNull() }) {
                        Text(stringResource(R.string.delete_cardRune))
                    }

                }
                if (showDetails) {
                    PillDetails(pillViewModel)
                }
                if (showAlertDialogSelectObject) {
                    AlertDialog(
                        onDismissRequest = {
                            showAlertDialogSelectObject = !showAlertDialogSelectObject
                        },
                        {},
                        dismissButton = {
                            TextButton(onClick = {
                                showAlertDialogSelectObject = !showAlertDialogSelectObject
                            }) {
                                Text(stringResource(R.string.exit))
                            }
                        },
                        text = {
                            Column(modifier = Modifier.fillMaxHeight()) {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    OutlinedTextField(
                                        value = search,
                                        onValueChange = { search = it },
                                        label = { Text(stringResource(R.string.search)) },
                                        singleLine = true
                                    )
                                }
                                Spacer(modifier = Modifier.size(height = 12.dp, width = 0.dp))
                                LazyColumn {
                                    val filtredPills =
                                        listAllPills.filter {
                                            it.name.contains(
                                                search,
                                                ignoreCase = true
                                            )
                                        }
                                    items(filtredPills) { pill ->
                                        Row(
                                            modifier = Modifier
                                                .border(0.4.dp, color = Color.Black)
                                                .fillMaxWidth()
                                                .padding(start = 5.dp)
                                                .clickable {
                                                    newCharacterViewModel.setPillCharacter(
                                                        pill
                                                    )
                                                    showAlertDialogSelectObject =
                                                        !showAlertDialogSelectObject
                                                },

                                            ) {
                                            AsyncImage(
                                                model = pill.imageUrl,
                                                contentDescription = pill.name,
                                                error = painterResource(R.drawable.facecard),
                                                modifier = Modifier.size(40.dp)
                                            )
                                            Text(text = pill.name, fontSize = 18.sp)

                                        }
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .padding(16.dp)
                            .size(width = 400.dp, height = 600.dp),

                        )
                }
                Column(
                    Modifier.padding(top = 50.dp)
                ) {
                    Button(onClick = {
                        selectPill = false
                        newCharacterViewModel.setPillCharacterNull()
                    }) {
                        Text(stringResource(R.string.back))
                    }
                }
            }
            if (selectCardRune) {
                Text(
                    text = stringResource(R.string.select_CardRune),
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                if (cardRuneNewCharacter == null) {
                    Button(onClick = {
                        showAlertDialogSelectObject = !showAlertDialogSelectObject
                    }) {
                        Text(stringResource(R.string.select_Object_list))
                    }
                } else {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            stringResource(R.string.start_object),
                            style = MaterialTheme.typography.h6
                        )
                        TextButton(onClick = {
                            cardRuneViewModel.onCardRuneClicked(cardRuneNewCharacter!!)
                            showDetails = !showDetails
                        }) {
                            Text(
                                text = cardRuneNewCharacter!!.name,
                                style = MaterialTheme.typography.h6
                            )
                        }
                    }
                    Button(onClick = { newCharacterViewModel.setCardRuneCharacterNull() }) {
                        Text(stringResource(R.string.delete_cardRune))
                    }

                }
                if (showDetails) {
                    CardRuneDetails(cardRuneViewModel)
                }
                if (showAlertDialogSelectObject) {
                    AlertDialog(
                        onDismissRequest = {
                            showAlertDialogSelectObject = !showAlertDialogSelectObject
                        },
                        {},
                        dismissButton = {
                            TextButton(onClick = {
                                showAlertDialogSelectObject = !showAlertDialogSelectObject
                            }) {
                                Text(stringResource(R.string.exit))
                            }
                        },
                        text = {
                            Column(modifier = Modifier.fillMaxHeight()) {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    OutlinedTextField(
                                        value = search,
                                        onValueChange = { search = it },
                                        label = { Text(stringResource(R.string.search)) },
                                        singleLine = true
                                    )
                                }
                                Spacer(modifier = Modifier.size(height = 12.dp, width = 0.dp))
                                LazyColumn {
                                    val filtredCardRunes =
                                        listAllCardRune.filter {
                                            it.name.contains(
                                                search,
                                                ignoreCase = true
                                            )
                                        }
                                    items(filtredCardRunes) { cardRune ->
                                        Row(
                                            modifier = Modifier
                                                .border(0.4.dp, color = Color.Black)
                                                .fillMaxWidth()
                                                .padding(start = 5.dp)
                                                .clickable {
                                                    newCharacterViewModel.setCardRuneCharacter(
                                                        cardRune
                                                    )
                                                    showAlertDialogSelectObject =
                                                        !showAlertDialogSelectObject
                                                },

                                            ) {
                                            AsyncImage(
                                                model = cardRune.imageUrl,
                                                contentDescription = cardRune.name,
                                                error = painterResource(R.drawable.facecard),
                                                modifier = Modifier.size(40.dp)
                                            )
                                            Text(text = cardRune.name, fontSize = 18.sp)

                                        }
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .padding(16.dp)
                            .size(width = 400.dp, height = 600.dp),

                        )
                }
                Column(
                    Modifier.padding(top = 50.dp)
                ) {
                    Button(onClick = {
                        selectCardRune = false
                        newCharacterViewModel.setCardRuneCharacterNull()
                    }) {
                        Text(stringResource(R.string.back))
                    }
                }
            }
        }

    }

}
