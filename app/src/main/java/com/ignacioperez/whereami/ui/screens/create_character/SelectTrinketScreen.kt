package com.ignacioperez.whereami.ui.screens.create_character

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ignacioperez.whereami.R
import com.ignacioperez.whereami.models.ListTrinkets
import com.ignacioperez.whereami.navigation.Routes
import com.ignacioperez.whereami.viewmodel.NewCharacterViewModel
import com.ignacioperez.whereami.viewmodel.TrinketViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectTrinketScreen(
    newCharacterViewModel: NewCharacterViewModel,
    trinketViewModel: TrinketViewModel,
    navController: NavController
) {
    var selectTrinket by rememberSaveable { mutableStateOf(false) }
    val trinketNewCharacter by newCharacterViewModel.trinketNewCharacter.observeAsState(initial = null)
    var showAlertDialogSelectTrinket by rememberSaveable { mutableStateOf(false) }
    val trinketList by trinketViewModel.allTrinkets.observeAsState(initial = (ListTrinkets()))
    var search by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.item_details)) }
            )
        },
        floatingActionButton = {
            if (selectTrinket && trinketNewCharacter != null) {
                FloatingActionButton(onClick = {
                    navController.navigate(Routes.SelectStatsScreen.route)
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
            if (!selectTrinket) {
                Text(
                    text = stringResource(R.string.trinket_select),
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                Button(onClick = {
                    selectTrinket = !selectTrinket
                    trinketViewModel.getAllTrinkets()
                }) {
                    Text(stringResource(R.string.start_trinket))
                }
                Button(onClick = {
                    navController.navigate(Routes.SelectStatsScreen.route)
                }) {
                    Text(stringResource(R.string.no_trinket))
                }
            } else {
                Text(
                    text = stringResource(R.string.select_trinket),
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                if (trinketNewCharacter == null) {
                    Button(onClick = {
                        showAlertDialogSelectTrinket = !showAlertDialogSelectTrinket
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
                            trinketViewModel.onTrinketClicked(trinketNewCharacter!!)
                            navController.navigate(Routes.TrinketDetailsScreen.route)
                        }) {
                            Text(trinketNewCharacter!!.name, style = MaterialTheme.typography.h6)
                        }
                    }
                    Column(
                        Modifier.padding(top = 50.dp)
                    ) {
                        Button(onClick = {
                            selectTrinket = !selectTrinket
                            newCharacterViewModel.setTrinketNull()
                        }) {
                            Text(stringResource(R.string.delete_selection))
                        }
                    }
                }
            }
            if (showAlertDialogSelectTrinket) {
                AlertDialog(
                    onDismissRequest = {
                        showAlertDialogSelectTrinket = !showAlertDialogSelectTrinket
                    },
                    {},
                    dismissButton = {
                        TextButton(onClick = {
                            showAlertDialogSelectTrinket = !showAlertDialogSelectTrinket
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
                                val filtredListTrinkets =
                                    trinketList.filter {
                                        it.name.contains(
                                            search,
                                            ignoreCase = true
                                        )
                                    }
                                items(filtredListTrinkets) { trinket ->
                                    Row(
                                        modifier = Modifier
                                            .border(0.4.dp, color = Color.Black)
                                            .fillMaxWidth()
                                            .padding(start = 5.dp)
                                            .clickable {
                                                newCharacterViewModel.setTrinketNewCharacter(
                                                    trinket
                                                )
                                                showAlertDialogSelectTrinket =
                                                    !showAlertDialogSelectTrinket
                                            },

                                        ) {
                                        AsyncImage(
                                            model = trinket.imageUrl,
                                            contentDescription = trinket.name,
                                            error = painterResource(R.drawable.facecard),
                                            modifier = Modifier.size(40.dp)
                                        )
                                        Text(text = trinket.name, fontSize = 18.sp)

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
        }
    }
}