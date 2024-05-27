package com.ignacioperez.whereami.ui.screens

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
import com.ignacioperez.whereami.models.ObjectChangeStats
import com.ignacioperez.whereami.models.Trinket
import com.ignacioperez.whereami.mycomposables.ObjectStatsChanged
import com.ignacioperez.whereami.viewmodel.TrinketViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrinketDetailsScreen(navController: NavController, trinketViewModel: TrinketViewModel) {
    val trinket: Trinket by trinketViewModel.selectedTrinket.observeAsState(
        initial = Trinket()
    )
    val stats: ObjectChangeStats by trinketViewModel.statsChangedByTrinket.observeAsState(
        ObjectChangeStats()
    )
    var showSpoiler by rememberSaveable { mutableStateOf(false) }

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
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back)
                )
            }
        }, actions = {
            if (trinket.unlockable) {
                Switch(checked = showSpoiler, onCheckedChange = { showSpoiler = it })
            }
        })

    }) {
        Column(Modifier.padding(it)) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = trinket.imageUrl,
                    placeholder = painterResource(R.drawable.trinket_curved_horn_icon),
                    error = painterResource(R.drawable.trinket_curved_horn_icon),
                    contentDescription = trinket.name,
                    modifier = Modifier
                        .size(90.dp)
                        .align(Alignment.CenterVertically)
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(top = 18.dp)
                        .fillMaxWidth(),
                ) {
                    Text(
                        text = trinket.name,
                        style = MaterialTheme.typography.headlineLarge,
                        textAlign = TextAlign.Center,

                        )
                    Row(
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = trinket.quote,
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
                    text = (stringResource(R.string.effect)) + trinket.description,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(8.dp))

                Spacer(modifier = Modifier.height(8.dp))
                ObjectStatsChanged(stats)
                if (trinket.unlockable) {
                    Spacer(Modifier.size(20.dp))
                    Column() {
                        Text(
                            text = stringResource(R.string.way_to_unlock),
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            if (showSpoiler) trinket.wayToUnlock else stringResource(R.string.secret),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }

            }

        }
    }
}
