package com.ignacioperez.whereami.ui.screens.create_character

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ignacioperez.whereami.R
import com.ignacioperez.whereami.models.Stat
import com.ignacioperez.whereami.viewmodel.CardRuneViewModel
import com.ignacioperez.whereami.viewmodel.ItemViewModel
import com.ignacioperez.whereami.viewmodel.NewCharacterViewModel
import com.ignacioperez.whereami.viewmodel.PillViewModel
import com.ignacioperez.whereami.viewmodel.TrinketViewModel
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectStatsScreen(
    newCharacterViewModel: NewCharacterViewModel,
    itemViewModel: ItemViewModel,
    cardRuneViewModel: CardRuneViewModel,
    pillViewModel: PillViewModel,
    trinketViewModel: TrinketViewModel
) {
    val pillNewCharacter by newCharacterViewModel.pillNewCharacter.observeAsState(null)
    val cardRuneNewCharacter by newCharacterViewModel.cardRuneNewCharacter.observeAsState(null)
    val trinketNewCharacter by newCharacterViewModel.trinketNewCharacter.observeAsState(null)
    val listItemsNewCharacter by newCharacterViewModel.listItemsNewCharacter.observeAsState(
        emptyList()
    )
    val statsChangedByPill by pillViewModel.statsChangedByPill.observeAsState(emptyList())
    val statsChangedByCardRune by cardRuneViewModel.statsChangedByCardRune.observeAsState(emptyList())
    val statsChangedByTrinket by trinketViewModel.statsChangedByTrinket.observeAsState(emptyList())
    val listStatsList = remember {
        mutableStateOf<List<Stat>>(emptyList())
    }

    val healthStat by newCharacterViewModel.healthStat.observeAsState(initial = 0.0)
    val damageStat by newCharacterViewModel.damageStat.observeAsState(initial = 0.0)
    val tearsStat by newCharacterViewModel.tearsStat.observeAsState(initial = 0.0)
    val shotSpeedStat by newCharacterViewModel.shotSpeedStat.observeAsState(initial = 0.0)
    val rangeStat by newCharacterViewModel.rangeStat.observeAsState(initial = 0.0)
    val luckStat by newCharacterViewModel.luckStat.observeAsState(initial = 0.0)
    val speedStat by newCharacterViewModel.speedStat.observeAsState(initial = 0.0)


    val initialStats = mutableListOf(
        Stat("Health", -1.0),
        Stat("Damage", 0.0),
        Stat("Tears", 0.0),
        Stat("Shot Speed", 0.0),
        Stat("Range", 0.0),
        Stat("Luck", 0.0),
        Stat("Speed", 0.0)
    )

    var healthComplete by rememberSaveable { mutableStateOf(false) }
    var damageComplete by rememberSaveable { mutableStateOf(false) }
    var tearsComplete by rememberSaveable { mutableStateOf(false) }
    var shotSpeedComplete by rememberSaveable { mutableStateOf(false) }
    var rangeComplete by rememberSaveable { mutableStateOf(false) }
    var luckComplete by rememberSaveable { mutableStateOf(false) }
    var speedComplete by rememberSaveable { mutableStateOf(false) }

    val statInfo = mapOf(
        "Health" to Pair(R.drawable.health_stat_icon, R.string.health_stat),
        "Speed" to Pair(R.drawable.speed_stat_icon, R.string.speed_stat),
        "Tears" to Pair(R.drawable.tears_stat_icon, R.string.tears_stat),
        "Damage" to Pair(R.drawable.damage_stat_icon, R.string.damage_stat),
        "Range" to Pair(R.drawable.range_stat_icon, R.string.range_stat),
        "Shot Speed" to Pair(R.drawable.shot_speed_stat_icon, R.string.shot_speed_stat),
        "Luck" to Pair(R.drawable.luck_stat_icon, R.string.luck_stat)
    )


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.item_details)) }
            )
        },
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                stringResource(R.string.select_stats),
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center
            )
            val pattern = remember { Regex("^\\d{0,2}(\\.\\d{0,2})?\$") }
            var input by rememberSaveable {
                mutableStateOf("")
            }
            Column() {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(R.drawable.health_stat_icon),
                        contentDescription = stringResource(R.string.health),
                        Modifier
                            .size(55.dp)
                            .padding(top = 20.dp)
                    )
                    Spacer(
                        Modifier
                            .width(20.dp)
                            .height(20.dp)
                    )
                    OutlinedTextField(
                        value = input,
                        onValueChange = {
                            if (it.isEmpty() || it.matches(pattern)) {
                                input = it
                                newCharacterViewModel.setHealthStat(it.toDoubleOrNull() ?: 0.0)

                            }
                        },
                        label = {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(stringResource(R.string.example))
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .size(width = 90.dp, height = 65.dp)


                    )
                }
                Spacer(Modifier.height(20.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = {

                    }) {
                        Text(stringResource(R.string.save_health))
                    }
                    Button(onClick = {

                    }) {
                        Text(stringResource(R.string.random_health))
                    }
                }
                Spacer(Modifier.height(40.dp))

            }

        }
    }
}


//if (listItemsNewCharacter.isEmpty() && trinketNewCharacter == null && cardRuneNewCharacter == null && pillNewCharacter == null) {
//    // Handle empty state
//} else {
//    Text(
//        stringResource(R.string.character_will_start),
//        style = MaterialTheme.typography.body1
//    )
//
//}
//LaunchedEffect(listItemsNewCharacter) {
//    val newListStats = mutableListOf<ObjectChangeStats>()
//    for (item in listItemsNewCharacter) {
//        newListStats.addAll(itemViewModel.loadStatsResponseObject(item.id))
//    }
//    listStatsList.value = newListStats
//}
//
//LaunchedEffect(cardRuneNewCharacter?.id) {
//    cardRuneNewCharacter?.id?.let { cardRuneViewModel.loadStats(it) }
//}
//LaunchedEffect(trinketNewCharacter?.id) {
//    trinketNewCharacter?.let { trinketViewModel.loadStats(it.id) }
//}
//LaunchedEffect(pillNewCharacter?.id) {
//    pillNewCharacter?.let { pillViewModel.loadStats(it.id) }
//}
//
//val updatedStats = remember(
//    statsChangedByCardRune,
//    statsChangedByTrinket,
//    statsChangedByPill,
//    listStatsList.value
//) {
//    val newStats = initialStats.toMutableList()
//
//    for (statCard in statsChangedByCardRune) {
//        val stat = newStats.find { it.name == statCard.name }
//        if (stat != null) {
//            stat.value += statCard.value
//        }
//    }
//
//    for (statTrinket in statsChangedByTrinket) {
//        val stat = newStats.find { it.name == statTrinket.name }
//        if (stat != null) {
//            stat.value += statTrinket.value
//        }
//    }
//
//    for (statPill in statsChangedByPill) {
//        val stat = newStats.find { it.name == statPill.name }
//        if (stat != null) {
//            stat.value += statPill.value
//        }
//    }
//
//    for (itemStat in listStatsList.value) {
//        val stat = newStats.find { it.name == itemStat.name }
//        if (stat != null) {
//            stat.value += itemStat.value
//        }
//    }
//    newStats
//}
fun getRandomHealth(): Double {
    val random = Random.nextDouble(0.0, 7.6).dec()
    return String.format("%.1f", random).toDouble()
}

@Composable
fun StatRow(statName: String, statValue: Double) {
    Row {
        Text(statName)
        Spacer(modifier = Modifier.width(8.dp))
        Text(statValue.toString())
    }
}