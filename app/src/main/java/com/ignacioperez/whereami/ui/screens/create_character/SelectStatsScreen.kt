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
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mode
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.ignacioperez.whereami.damageRegex
import com.ignacioperez.whereami.healthRegex
import com.ignacioperez.whereami.luckRegex
import com.ignacioperez.whereami.models.Stat
import com.ignacioperez.whereami.mycomposables.ModifyStatSelected
import com.ignacioperez.whereami.mycomposables.StatTextField
import com.ignacioperez.whereami.rangeRegex
import com.ignacioperez.whereami.shotSpeedRegex
import com.ignacioperez.whereami.speedRegex
import com.ignacioperez.whereami.tearsRegex
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

    val healthStat by newCharacterViewModel.healthStat.observeAsState(initial = -1.0)
    val damageStat by newCharacterViewModel.damageStat.observeAsState(initial = -1.0)
    val tearsStat by newCharacterViewModel.tearsStat.observeAsState(initial = -1.0)
    val shotSpeedStat by newCharacterViewModel.shotSpeedStat.observeAsState(initial = -1.0)
    val rangeStat by newCharacterViewModel.rangeStat.observeAsState(initial = -1.0)
    val luckStat by newCharacterViewModel.luckStat.observeAsState(initial = -1.0)
    val speedStat by newCharacterViewModel.speedStat.observeAsState(initial = -1.0)


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

    val statIcon = listOf(
        Pair(R.drawable.health_stat_icon, R.string.health_stat),
        Pair(R.drawable.speed_stat_icon, R.string.speed_stat),
        Pair(R.drawable.tears_stat_icon, R.string.tears_stat),
        Pair(R.drawable.damage_stat_icon, R.string.damage_stat),
        Pair(R.drawable.range_stat_icon, R.string.range_stat),
        Pair(R.drawable.shot_speed_stat_icon, R.string.shot_speed_stat),
        Pair(R.drawable.luck_stat_icon, R.string.luck_stat)
    )
    var indexIcon by rememberSaveable { mutableStateOf(0) }
    val pattern = remember { Regex("^\\d{0,2}(\\.\\d{0,2})?\$") }


    var healthInput by rememberSaveable {
        mutableStateOf(
            ""
        )
    }
    var speedInput by rememberSaveable { mutableStateOf("") }
    var tearsInput by rememberSaveable { mutableStateOf("") }
    var damageInput by rememberSaveable { mutableStateOf("") }
    var rangeInput by rememberSaveable { mutableStateOf("") }
    var luckInput by rememberSaveable { mutableStateOf("") }
    var shotSpeedInput by rememberSaveable { mutableStateOf("") }
    val statInfo = listOf(
        "Health" to Pair(R.drawable.health_stat_icon, R.string.health_stat),
        "Speed" to Pair(R.drawable.speed_stat_icon, R.string.speed_stat),
        "Tears" to Pair(R.drawable.tears_stat_icon, R.string.tears_stat),
        "Damage" to Pair(R.drawable.damage_stat_icon, R.string.damage_stat),
        "Range" to Pair(R.drawable.range_stat_icon, R.string.range_stat),
        "Shot Speed" to Pair(R.drawable.shot_speed_stat_icon, R.string.shot_speed_stat),
        "Luck" to Pair(R.drawable.luck_stat_icon, R.string.luck_stat)
    )
    var show by rememberSaveable { mutableStateOf(false) }

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

            Column() {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(statIcon[indexIcon].first),
                        contentDescription = stringResource(statIcon[indexIcon].second),
                        Modifier
                            .size(55.dp)
                            .padding(top = 20.dp)
                    )
                    Spacer(
                        Modifier
                            .width(20.dp)
                            .height(20.dp)
                    )

                    if (!healthComplete) {
                        StatTextField(
                            statString = healthInput,
                            onStatChange = {
                                healthInput = it
                            },
                            pattern = healthRegex,
                            example = R.string.example
                        )
                    } else if (!speedComplete) {
                        StatTextField(
                            statString = speedInput,
                            onStatChange = {
                                speedInput = it
                            },
                            pattern = speedRegex,
                            example = R.string.example
                        )
                    } else if (!tearsComplete) {
                        StatTextField(
                            statString = tearsInput,
                            onStatChange = {
                                tearsInput = it
                            },
                            pattern = tearsRegex,
                            example = R.string.example
                        )
                    } else if (!damageComplete) {
                        StatTextField(
                            statString = damageInput,
                            onStatChange = {
                                damageInput = it
                            },
                            pattern = damageRegex,
                            example = R.string.example
                        )
                    } else if (!rangeComplete) {
                        StatTextField(
                            statString = rangeInput,
                            onStatChange = {
                                rangeInput = it
                            },
                            pattern = rangeRegex,
                            example = R.string.example
                        )
                    } else if (!shotSpeedComplete) {
                        StatTextField(
                            statString = shotSpeedInput,
                            onStatChange = {
                                shotSpeedInput = it
                            },
                            pattern = shotSpeedRegex,
                            example = R.string.example
                        )
                    } else if (!luckComplete) {
                        StatTextField(
                            statString = luckInput,
                            onStatChange = {
                                luckInput = it
                            },
                            pattern = luckRegex,
                            example = R.string.example
                        )
                    }
                }


            }
            var test by rememberSaveable { mutableStateOf(false) }
            Spacer(Modifier.height(20.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = {
                    if (!healthComplete) {
                        healthComplete = true
                        newCharacterViewModel.setHealthStat(healthInput.toDoubleOrNull() ?: 1.0)
                        indexIcon += 1

                    } else if (!speedComplete) {
                        speedComplete = true
                        newCharacterViewModel.setSpeedStat(speedInput.toDoubleOrNull() ?: 1.0)
                        indexIcon += 1
                    } else if (!tearsComplete) {
                        tearsComplete = true
                        newCharacterViewModel.setTearsStat(tearsInput.toDoubleOrNull() ?: 1.0)
                        indexIcon += 1
                    } else if (!damageComplete) {
                        damageComplete = true
                        newCharacterViewModel.setDamageStat(damageInput.toDoubleOrNull() ?: 1.0)
                        indexIcon += 1
                    } else if (!rangeComplete) {
                        rangeComplete = true
                        newCharacterViewModel.setRangeStat(rangeInput.toDoubleOrNull() ?: 1.0)
                        indexIcon += 1
                    } else if (!shotSpeedComplete) {
                        shotSpeedComplete = true
                        newCharacterViewModel.setShotSpeedStat(
                            shotSpeedInput.toDoubleOrNull() ?: 1.0
                        )
                        indexIcon += 1
                    } else if (!luckComplete) {
                        luckComplete = true
                        newCharacterViewModel.setLuckStat(luckInput.toDoubleOrNull() ?: 1.0)

                    }
                    if (healthComplete && speedComplete && tearsComplete && damageComplete && rangeComplete && shotSpeedComplete && luckComplete) {
                        test = !test
                    }

                }) {
                    Text(stringResource(R.string.save_stat))
                }
                Button(onClick = {
                    generateRandomStats(newCharacterViewModel)
                    healthComplete = true
                    speedComplete = true
                    tearsComplete = true
                    damageComplete = true
                    rangeComplete = true
                    shotSpeedComplete = true
                    luckComplete = true

                }) {
                    Text(stringResource(R.string.random_stat))
                }
            }
            if (test) {
                AlertDialog(
                    onDismissRequest = { test = false },
                    confirmButton = {
                        TextButton(onClick = { test = false }) {
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
            Spacer(Modifier.height(40.dp))
            if (healthComplete) {
                Row() {
                    Icon(
                        painterResource(statIcon[0].first),
                        contentDescription = stringResource(statIcon[0].second),
                        modifier = Modifier.size(20.dp)
                    )
                    Text(stringResource(R.string.healt) + healthStat.toString())
                }
            }
            if (speedComplete) {
                Row() {
                    Icon(
                        painterResource(statIcon[1].first),
                        contentDescription = stringResource(statIcon[1].second),
                        modifier = Modifier.size(20.dp)
                    )
                    Text(stringResource(R.string.speed) + speedStat.toString())
                }
            }
            if (tearsComplete) {
                Row() {
                    Icon(
                        painterResource(statIcon[2].first),
                        contentDescription = stringResource(statIcon[2].second),
                        modifier = Modifier.size(20.dp)
                    )
                    Text(stringResource(R.string.tears) + tearsStat.toString())
                }
            }
            if (damageComplete) {
                Row() {
                    Icon(
                        painterResource(statIcon[3].first),
                        contentDescription = stringResource(statIcon[4].second),
                        modifier = Modifier.size(20.dp)
                    )
                    Text(stringResource(R.string.damage) + damageStat.toString())
                }
            }
            if (rangeComplete) {
                Row() {
                    Icon(
                        painterResource(statIcon[4].first),
                        contentDescription = stringResource(statIcon[4].second),
                        modifier = Modifier.size(20.dp)
                    )

                }
            }
            if (shotSpeedComplete) {
                Row() {
                    Icon(
                        painterResource(statIcon[5].first),
                        contentDescription = stringResource(statIcon[5].second),
                        modifier = Modifier.size(20.dp)
                    )
                    Text(stringResource(R.string.shot_speed) + shotSpeedStat.toString())
                    IconButton(onClick = { show = !show }) {
                        Icon(
                            imageVector = Icons.Filled.Mode,
                            contentDescription = stringResource(R.string.modify)
                        )
                    }
                    if (show) {
                        ModifyStatSelected(
                            newCharacterViewModel = newCharacterViewModel,
                            show = show,
                            onDismissRequest = { show = false }, // Asigna la variable `show` aquí
                            currentStat = newCharacterViewModel.shotSpeedStat.value ?: -1.0,
                            pattern = healthRegex, // Utiliza el nombre de la variable, no solo el valor
                            example = R.string.example, // Utiliza el nombre de la variable, no solo el valor
                        )
                    }
                }
            }
            if (luckComplete) {
                Row() {
                    Icon(
                        painterResource(statIcon[6].first),
                        contentDescription = stringResource(statIcon[6].second),
                        modifier = Modifier.size(20.dp)
                    )
                    Text(stringResource(R.string.luck) + luckStat.toString())
                }
            }
            if (show) {
                ModifyStatSelected(
                    newCharacterViewModel = newCharacterViewModel,
                    show = show,
                    onDismissRequest = { show = false }, // Asigna la variable `show` aquí
                    currentStat = newCharacterViewModel.shotSpeedStat.value ?: -1.0,
                    pattern = healthRegex, // Utiliza el nombre de la variable, no solo el valor
                    example = R.string.example, // Utiliza el nombre de la variable, no solo el valor
                )
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

    // Main function to generate stats based on rules

}

fun getRandomHealth(): Double {
    var health: Double
    do {
        health = Random.nextDouble(0.0, 12.0)
    } while (!healthRegex.matches(String.format("%.1f", health)))
    return String.format("%.1f", health).toDouble()
}

fun getRandomSpeed(): Double {
    var speed: Double
    do {
        speed = Random.nextDouble(1.0, 1.5)
    } while (!speedRegex.matches(String.format("%.1f", speed)))
    return String.format("%.1f", speed).toDouble()
}

fun getRandomDamage(): Double {
    var damage: Double
    do {
        damage = Random.nextDouble(1.0, 10.0)
    } while (!damageRegex.matches(String.format("%.1f", damage)))
    return String.format("%.1f", damage).toDouble()
}

fun getRandomTears(): Double {
    var tears: Double
    do {
        tears = Random.nextDouble(1.0, 3.0)
    } while (!tearsRegex.matches(String.format("%.1f", tears)))
    return String.format("%.1f", tears).toDouble()
}

fun getRandomRange(): Double {
    var range: Double
    do {
        range = Random.nextDouble(7.0, 14.0)
    } while (!rangeRegex.matches(String.format("%.1f", range)))
    return String.format("%.1f", range).toDouble()
}

fun getRandomShotSpeed(): Double {
    var shotSpeed: Double
    do {
        shotSpeed = Random.nextDouble(1.0, 2.0)
    } while (!shotSpeedRegex.matches(String.format("%.1f", shotSpeed)))
    return String.format("%.1f", shotSpeed).toDouble()
}

fun getRandomLuck(): Double {
    var luck: Double
    do {
        luck = Random.nextDouble(-2.0, 1.5)
    } while (!luckRegex.matches(String.format("%.1f", luck)))
    return String.format("%.1f", luck).toDouble()
}

fun generateRandomStats(newCharacterViewModel: NewCharacterViewModel) {
    var health = getRandomHealth()
    var speed = getRandomSpeed()
    var damage = getRandomDamage()
    var tears = getRandomTears()
    var range = getRandomRange()
    var shotSpeed = getRandomShotSpeed()
    var luck = getRandomLuck()

    if (health >= 6.0) {
        speed = getRandomSpeed()
    } else if (health <= 3.0) {
        if (Random.nextBoolean()) {
            damage = getRandomDamage()
            tears = getRandomTears()
        } else {
            tears = getRandomTears()
            damage = getRandomDamage()
        }
        speed = getRandomSpeed()
    } else if (damage >= 5.0) {
        health = getRandomHealth()
        tears = getRandomTears()
    } else if (tears >= 2.5) {
        damage = getRandomDamage()
    }

    newCharacterViewModel.setHealthStat(health)
    newCharacterViewModel.setSpeedStat(speed)
    newCharacterViewModel.setDamageStat(damage)
    newCharacterViewModel.setTearsStat(tears)
    newCharacterViewModel.setRangeStat(range)
    newCharacterViewModel.setShotSpeedStat(shotSpeed)
    newCharacterViewModel.setLuckStat(luck)

}