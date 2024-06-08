package com.ignacioperez.whereami.ui.screens.characters

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ignacioperez.whereami.R
import com.ignacioperez.whereami.checkStatsChanges
import com.ignacioperez.whereami.models.CharacterResponse
import com.ignacioperez.whereami.models.Item
import com.ignacioperez.whereami.models.StatResponse
import com.ignacioperez.whereami.models.StatsModifiedCharacter
import com.ignacioperez.whereami.navigation.Routes
import com.ignacioperez.whereami.viewmodel.CharacterViewModel
import com.ignacioperez.whereami.viewmodel.ItemViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetails(
    navController: NavController,
    characterViewModel: CharacterViewModel,
    itemViewModel: ItemViewModel
) {
    val character: CharacterResponse by characterViewModel.selectedCharacter.observeAsState(
        CharacterResponse()
    )
    val statsBase: StatResponse by characterViewModel.statsBaseCharacter.observeAsState(
        StatResponse()
    )
    val statsModified: StatsModifiedCharacter by characterViewModel.statsModified.observeAsState(
        StatsModifiedCharacter()
    )
    val itemsCharacter: List<Item> by characterViewModel.itemsCharacter.observeAsState(
        emptyList()
    )
    var showSpoiler by rememberSaveable() { mutableStateOf(false) }
    var scrollstate = rememberScrollState()

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
            if (character.unlockable) {
                TextButton(onClick = { showSpoiler = !showSpoiler }) {
                    Text(stringResource(R.string.show_spoiler))
                }
            }
        })
    }) {
        Column(
            modifier = Modifier
                .padding(it)
                .verticalScroll(scrollstate),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = character.imageUrl,
                    placeholder = painterResource(R.drawable.isaac_icon),
                    error = painterResource(R.drawable.isaac_icon),
                    contentDescription = character.name,
                    modifier = Modifier
                        .size(150.dp)
                        .padding(start = 35.dp, top = 3.5.dp)

                )
                AsyncImage(
                    model = character.transitionImage,
                    placeholder = painterResource(R.drawable.isaac_icon),
                    error = painterResource(R.drawable.isaac_icon),
                    contentDescription = character.name,
                    modifier = Modifier
                        .size(190.dp)
                        .padding(start = 25.dp)

                )
            }
            Divider(
                modifier = Modifier.fillMaxWidth()

            )

            Column(
                Modifier
                    .border(width = 1.dp, color = Color.Black)
                    .fillMaxWidth()
                    .fillMaxSize()
                    .padding(15.dp)
            ) {
                Column(Modifier.padding(top = 5.dp)) {
                    Text(
                        text = stringResource(R.string.name) + character.name,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = if (character.name == "Isaac") {
                            stringResource(R.string.way_to_unlock) + stringResource(R.string.default_character)
                        } else {
                            stringResource(R.string.way_to_unlock) +
                                    if (showSpoiler) character.wayToUnlock else stringResource(R.string.secret)
                        }, style = MaterialTheme.typography.titleLarge
                    )
                }
                Text(
                    text = stringResource(R.string.starting_items),
                    style = MaterialTheme.typography.headlineSmall
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (item in itemsCharacter) {
                        ItemOnAltarClickable(item = item, itemViewModel, navController)
                    }
                }
                StatsDefaultCharacter(statsBase, statsModified)
            }
        }
    }

}

@Composable
fun StatsDefaultCharacter(

    statsBase: StatResponse, statsModified: StatsModifiedCharacter
) {
    val statInfo = mapOf(
        "Health" to Pair(R.drawable.health_stat_icon, R.string.health_stat),
        "Speed" to Pair(R.drawable.speed_stat_icon, R.string.speed_stat),
        "Tears" to Pair(R.drawable.tears_stat_icon, R.string.tears_stat),
        "Damage" to Pair(R.drawable.damage_stat_icon, R.string.damage_stat),
        "Range" to Pair(R.drawable.range_stat_icon, R.string.range_stat),
        "Shot Speed" to Pair(R.drawable.shot_speed_stat_icon, R.string.shot_speed_stat),
        "Luck" to Pair(R.drawable.luck_stat_icon, R.string.luck_stat)
    )
    val statsEquals = checkStatsChanges(statsBase, statsModified)
    if (statsEquals) {
        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(R.string.stats_base))
            for (stat in statsBase) {
                val info = statInfo[stat.name]
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(info!!.first),
                        contentDescription = stringResource(info.second),
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(
                        modifier = Modifier.size(8.dp)
                    )
                    Text(text = stat.name + ": ")
                    Text(text = stat.value.toString())
                }
            }
        }
    } else {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.stats_base),
                )
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = stringResource(R.string.arrow),
                    modifier = Modifier.size(30.dp)
                )
                Text(
                    text = stringResource(R.string.stats_modified),

                    )
            }
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    for (stat in statsBase) {
                        val info = statInfo[stat.name]
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(info!!.first),
                                contentDescription = stringResource(info.second),
                                modifier = Modifier.size(30.dp)
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            Text(text = stat.name + ": ")
                            Text(text = stat.value.toString())
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 22.dp)
                ) {
                    for (stat in statsModified) {
                        val info = statInfo[stat.name]
                        val baseStat = statsBase.find { it.name == stat.name }
                        val color = when {
                            baseStat == null -> Color.Unspecified
                            stat.total_value > baseStat.value -> Color.Green
                            stat.total_value < baseStat.value -> Color.Red
                            else -> Color.Unspecified
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(info!!.first),
                                contentDescription = stringResource(info.second),
                                modifier = Modifier.size(30.dp)
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            Text(text = stat.name + ": ")
                            Text(text = stat.total_value.toString(), color = color)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ItemOnAltarClickable(item: Item, itemViewModel: ItemViewModel, navController: NavController) {
    Column(modifier = Modifier
        .padding(start = 5.dp)
        .clickable {
            itemViewModel.onItemClicked(item = item)
            navController.navigate(route = Routes.ItemDetailsScreen.route)
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
}