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
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ignacioperez.whereami.R
import com.ignacioperez.whereami.models.Item
import com.ignacioperez.whereami.models.ItemChangeStats
import com.ignacioperez.whereami.mycomposables.TopAppBarExit
import com.ignacioperez.whereami.viewmodel.ItemViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailsScreen(navController: NavController, itemViewModel: ItemViewModel) {
    val item: Item by itemViewModel.selectedItem.observeAsState(
        Item()
    )
    val stats: ItemChangeStats by itemViewModel.statsChangedByItem.observeAsState(
        ItemChangeStats()
    )
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = stringResource(
                    id = R.string.item_details
                )
            )
        }, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back)
                )
            }
        }, actions = {
            if (item.unlockable) {
                var showSpoiler by rememberSaveable() { mutableStateOf(false) }
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
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.headlineLarge,
                        textAlign = TextAlign.Center,

                        )
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
                    .fillMaxWidth()
                    .border(1.dp, Color.Black)
                    .padding(16.dp)
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
                ItemStatsChanged(stats)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { /* TODO */ }) {
                    Text(text = "Botón")
                }
                Button(onClick = { /* TODO */ }) {
                    Text(text = "Spoiler: si o no")
                }
            }
        }
    }
//        Column(
//            modifier = Modifier
//                .padding(it)
//                .padding(16.dp)
//        ) {
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                ItemOnAltar(item)
//                Spacer(modifier = Modifier.width(16.dp))
//                Column(
//                    verticalArrangement = Arrangement.Center,
//                    modifier = Modifier.weight(1f),
//                ) {
//                    Text(
//                        text = item.name,
//                        style = MaterialTheme.typography.headlineMedium,
//                        textAlign = TextAlign.Center
//                    )
//                    Text(
//                        text = item.quote,
//                        style = MaterialTheme.typography.headlineSmall,
//                        textAlign = TextAlign.Center
//                    )
//                }
//            }
//            Spacer(modifier = Modifier.height(16.dp))
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .border(1.dp, Color.Black)
//                    .padding(16.dp)
//            ) {
//                Text(text = "Efect: ${item.name}")
//                Spacer(modifier = Modifier.height(8.dp))
//                Text(text = "Type: ${item.unlockable}")
//                Spacer(modifier = Modifier.height(8.dp))
//                Text(text = "Stats:")
//                Text(text = "- tear: ${item.wayToUnlock}")
//                Text(text = "- damage: ${item.wayToUnlock}")
//            }
//            Spacer(modifier = Modifier.height(16.dp))
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Button(onClick = { /* TODO */ }) {
//                    Text(text = "Botón")
//                }
//                Button(onClick = { /* TODO */ }) {
//                    Text(text = "Spoiler: si o no")
//                }
//            }
//        }
//    }


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
            contentDescription = "The delasign logo",
            modifier = Modifier.size(80.dp)
                .padding(start = 7.dp, top = 3.5.dp)
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

@Composable
fun ItemStatsChanged(itemChangeStats: ItemChangeStats) {
    for (stat in itemChangeStats) {
        Text(text = stat.name + " " + stat.value.toString())
    }
}