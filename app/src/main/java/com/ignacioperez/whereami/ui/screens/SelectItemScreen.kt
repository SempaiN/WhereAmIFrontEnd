package com.ignacioperez.whereami.ui.screens

import android.util.Log
import androidx.collection.intFloatMapOf
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ignacioperez.whereami.R
import com.ignacioperez.whereami.checkDoubleActivatedItem
import com.ignacioperez.whereami.models.Item
import com.ignacioperez.whereami.models.ObjectChangeStatsList
import com.ignacioperez.whereami.mycomposables.ObjectStatsChanged
import com.ignacioperez.whereami.navigation.Routes
import com.ignacioperez.whereami.viewmodel.ItemViewModel
import com.ignacioperez.whereami.viewmodel.NewCharacterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectItemScreen(
    newCharacterViewModel: NewCharacterViewModel,
    itemViewModel: ItemViewModel,
    navController: NavController
) {
    itemViewModel.getAllItems()
    var showAlertDialogSelectItem by rememberSaveable {
        mutableStateOf(false)
    }
    var search by rememberSaveable {
        mutableStateOf("")
    }
    var showAlertDialogDuplicateItem by rememberSaveable {
        mutableStateOf(false)
    }
    var showAlertDialogDoubleActivated by rememberSaveable {
        mutableStateOf(false)
    }
    var showAlertDialogStatsChangedByItem by rememberSaveable {
        mutableStateOf(false)
    }
    val listAllItems: List<Item> by itemViewModel.allItems.observeAsState(initial = emptyList())
    val listItemsNewCharacter: List<Item> by newCharacterViewModel.listItemsNewCharacter.observeAsState(
        initial = emptyList()
    )

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = stringResource(
                    id = R.string.item_details
                )
            )
        })
    }) {
        Column(
            Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Text(
                stringResource(R.string.how_many_items),
                style = MaterialTheme.typography.h5,
                textAlign = TextAlign.Center
            )
            if (listItemsNewCharacter.size < 3) {
                Button(onClick = {
                    showAlertDialogSelectItem = !showAlertDialogSelectItem
                }, modifier = Modifier.padding(vertical = 10.dp)) {
                    Text(text = stringResource(R.string.select_item))
                }
            }
            if (listItemsNewCharacter.isEmpty()) {
                Text(stringResource(R.string.no_starting_items))
            } else {
                Row(
                    modifier = Modifier.padding(top = 80.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    for (item in listItemsNewCharacter) {
                        ItemOnAltarClickableNewCharacter(
                            item,
                            itemViewModel,
                            navController,
                            newCharacterViewModel
                        )
                    }
                }
            }
            Button(onClick = {
                navController.navigate(Routes.SelectCardRunePillScreen.route)
                Log.i("PacoItems", listItemsNewCharacter.toString())
            }) {
                Text(stringResource(R.string.next))
            }
        }
        if (showAlertDialogSelectItem) {
            AlertDialog(
                onDismissRequest = {
                    showAlertDialogSelectItem = !showAlertDialogSelectItem
                },
                {},
                dismissButton = {
                    TextButton(onClick = {
                        showAlertDialogSelectItem = !showAlertDialogSelectItem
                    }) {
                        Text(text = stringResource(R.string.exit))
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
                            val filteredItems =
                                listAllItems.filter { it.name.contains(search, ignoreCase = true) }
                            items(filteredItems) { item ->
                                Row(
                                    modifier = Modifier
                                        .border(0.4.dp, color = Color.Black)
                                        .fillMaxWidth()
                                        .padding(start = 5.dp)
                                        .clickable {
                                            if (listItemsNewCharacter.contains(item)) {
                                                showAlertDialogSelectItem = false
                                                showAlertDialogDuplicateItem = true
                                            } else if (checkDoubleActivatedItem(
                                                    item,
                                                    listItemsNewCharacter
                                                )
                                            ) {
                                                showAlertDialogDoubleActivated = true
                                                showAlertDialogSelectItem = false
                                            } else {
                                                newCharacterViewModel.addNewItemToList(item = item)
                                                showAlertDialogSelectItem = false
                                            }
                                        },
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    AsyncImage(
                                        model = item.imageUrl,
                                        contentDescription = item.name,
                                        error = painterResource(R.drawable.godhead_icon),
                                        modifier = Modifier.size(60.dp)
                                    )
                                    Text(
                                        text = item.name,
                                        fontSize = 17.sp,
                                    )
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
        if (showAlertDialogDuplicateItem) {
            AlertDialog(
                onDismissRequest = { showAlertDialogDuplicateItem = false },
                confirmButton = {
                    TextButton(onClick = { showAlertDialogDuplicateItem = false }) {
                        Text(
                            text = stringResource(R.string.exit),
                            color = MaterialTheme.colors.error
                        )
                    }
                },
                title = {
                    Text(
                        text = stringResource(R.string.item_already_added),
                        color = MaterialTheme.colors.error
                    )
                },
                text = {
                    Text(
                        text = stringResource(R.string.item_already_added),
                        color = MaterialTheme.colors.error
                    )
                }
            )
        }
        if (showAlertDialogDoubleActivated) {
            AlertDialog(
                onDismissRequest = { showAlertDialogDoubleActivated = false },
                confirmButton = {
                    TextButton(onClick = { showAlertDialogDoubleActivated = false }) {
                        Text(
                            text = stringResource(R.string.exit),
                            color = MaterialTheme.colors.onError
                        )
                    }
                },
                title = {
                    Text(
                        text = stringResource(R.string.activatable_already_added),
                        color = MaterialTheme.colors.onError
                    )
                },
                text = {
                    Text(
                        text = stringResource(R.string.activatable_already_added_explain),
                        color = MaterialTheme.colors.onError
                    )
                },
                containerColor = MaterialTheme.colors.error
            )
        }


    }

}


@Composable
fun ItemOnAltarClickableNewCharacter(
    item: Item,
    itemViewModel: ItemViewModel,
    navController: NavController,
    newCharacterViewModel: NewCharacterViewModel
) {
    val stats: ObjectChangeStatsList by itemViewModel.statsChangedByItem.observeAsState(
        ObjectChangeStatsList()
    )
    var showAlertDialogStatsChangedByItem by rememberSaveable {
        mutableStateOf(false)
    }
    Column(modifier = Modifier
        .padding(start = 5.dp)
        .pointerInput(Unit) {
            detectTapGestures(
                onLongPress = {
                    itemViewModel.onItemClicked(item = item)
                    navController.navigate(route = Routes.ItemDetailsScreen.route)
                },
                onDoubleTap = {
                    newCharacterViewModel.removeItemFromList(item)
                },
                onTap = {
                    itemViewModel.loadStats(item.id)
                    showAlertDialogStatsChangedByItem = true
                }
            )
        }) {
        AsyncImage(
            model = item.imageUrl,
            placeholder = painterResource(id = R.drawable.godhead_icon),
            error = painterResource(id = R.drawable.godhead_icon),
            contentDescription = item.name,
            modifier = Modifier
                .size(80.dp)
                .padding(start = 9.dp, top = 3.5.dp)
        )
        Image(
            painter = painterResource(R.drawable.item_altar),
            contentDescription = "",
            modifier = Modifier
                .size(80.dp)
                .padding(start = 7.dp)
        )
    }
    if (showAlertDialogStatsChangedByItem) {
        AlertDialog(
            onDismissRequest = { showAlertDialogStatsChangedByItem = false },
            confirmButton = {
                TextButton(onClick = { showAlertDialogStatsChangedByItem = false }) {
                    Text(text = stringResource(R.string.exit))
                }
            },
            title = {
                Text(text = stringResource(R.string.stats_changed_by_item))
            },
            text = {
                Column(modifier = Modifier.wrapContentHeight()) {
                    ObjectStatsChanged(stats)
                }
            },

            )
    }

}

