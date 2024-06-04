package com.ignacioperez.whereami.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ignacioperez.whereami.R
import com.ignacioperez.whereami.models.Item
import com.ignacioperez.whereami.models.ObjectChangeStatsList
import com.ignacioperez.whereami.models.User
import com.ignacioperez.whereami.mycomposables.ObjectStatsChanged
import com.ignacioperez.whereami.viewmodel.ItemViewModel
import com.ignacioperez.whereami.viewmodel.SignInViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailsScreen(
    navController: NavController,
    itemViewModel: ItemViewModel,
    signInViewModel: SignInViewModel
) {
    val item: Item by itemViewModel.selectedItem.observeAsState(
        Item()
    )
    val isFavoriteItem: Boolean by itemViewModel.isSelectedItemFavorite.observeAsState(false)
    val user: User by signInViewModel.user.observeAsState(
        initial = User()
    )
    itemViewModel.checkFavoriteItem(item, user)
    val stats: ObjectChangeStatsList by itemViewModel.statsChangedByItem.observeAsState(
        ObjectChangeStatsList()
    )
    var showSpoiler by rememberSaveable() { mutableStateOf(false) }
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = stringResource(
                    id = R.string.item_details
                )
            )
        }, navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
                itemViewModel.clearStats()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back)
                )
            }
        }, actions = {
            if (item.unlockable) {
                Switch(checked = showSpoiler, onCheckedChange = { showSpoiler = it })
            }
        })

    }) {
        Column(Modifier.padding(it)) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                ItemOnAltar(item)
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(top = 18.dp)
                        .fillMaxWidth(),
                ) {
                    Row {
                        Text(
                            text = item.name,
                            style = MaterialTheme.typography.headlineLarge,
                            textAlign = TextAlign.Center,
                        )
                        IconButton(onClick = {
                            if (isFavoriteItem) {
                                itemViewModel.deleteItemFavorite(item, user)

                            } else {
                                itemViewModel.insertItemFavorite(item, user)

                            }
                        }) {
                            Icon(
                                imageVector = if (isFavoriteItem) Icons.Default.Favorite else Icons.Outlined.Favorite,
                                contentDescription = "Seleccionar como favorito",
                                tint = if (isFavoriteItem) Color.Red else Color.Black
                            )
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = item.quote,
                            style = MaterialTheme.typography.headlineMedium,
                            fontStyle = FontStyle.Italic,
                            textAlign = TextAlign.Center,
                        )

                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .border(1.dp, Color.Black)
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = (stringResource(R.string.effect)) + item.description,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                if ((item.charges == -1) or (item.charges > 0)) {
                    Text(text = stringResource(R.string.type) + stringResource(R.string.active))
                    if (item.charges == -1) {
                        Text(
                            text = stringResource(R.string.charges) + stringResource(
                                R.string.unlimited
                            )
                        )
                    } else {
                        Text(
                            text = stringResource(R.string.charges) + item.charges.toString()
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                ObjectStatsChanged(stats)
                if (item.unlockable) {
                    Row() {
                        Text(text = stringResource(R.string.way_to_unlock))
                        Text(if (showSpoiler) item.wayToUnlock else stringResource(R.string.secret))
                    }
                }

            }

        }
    }


}

@Composable
fun ItemOnAltar(item: Item) {
    Column(
        modifier = Modifier.padding(start = 5.dp)
    ) {
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
}



