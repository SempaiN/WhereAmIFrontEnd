package com.ignacioperez.whereami.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ignacioperez.whereami.R
import com.ignacioperez.whereami.models.Item
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
            Button(onClick = {
                showAlertDialogSelectItem = !showAlertDialogSelectItem
            }, modifier = Modifier.padding(vertical = 10.dp)) {
                Text(text = stringResource(R.string.select_item))
            }
            if (listItemsNewCharacter.isEmpty()) {
                Text(stringResource(R.string.no_starting_items))
            } else {
                Row() {
                    for (item in listItemsNewCharacter) {
                        ItemOnAltarClickable(item, itemViewModel, navController)
                    }
                }
            }
        }
        if (showAlertDialogSelectItem) {
            AlertDialog(
                onDismissRequest = {
                    showAlertDialogSelectItem = !showAlertDialogSelectItem
                },
                {

                },
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
                            verticalAlignment = Alignment.CenterVertically,

                            ) {
                            OutlinedTextField(
                                value = search,
                                onValueChange = { search = it },
                                label = { Text("Vuca") },
                                singleLine = true
                            )
                        }
                        Spacer(modifier = Modifier.size(height = 12.dp, width = 0.dp))
                        LazyColumn(

                        ) {
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

    }

}
