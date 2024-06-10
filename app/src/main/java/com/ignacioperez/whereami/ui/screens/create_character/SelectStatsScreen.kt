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
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Mode
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ignacioperez.whereami.R
import com.ignacioperez.whereami.damageRegex
import com.ignacioperez.whereami.healthRegex
import com.ignacioperez.whereami.listImagesAppearances
import com.ignacioperez.whereami.luckRegex
import com.ignacioperez.whereami.models.NewCharacter
import com.ignacioperez.whereami.models.Stat
import com.ignacioperez.whereami.mycomposables.StatTextField
import com.ignacioperez.whereami.navigation.Routes
import com.ignacioperez.whereami.rangeRegex
import com.ignacioperez.whereami.shotSpeedRegex
import com.ignacioperez.whereami.speedRegex
import com.ignacioperez.whereami.tearsRegex
import com.ignacioperez.whereami.transitionImageIsaac
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
    navController: NavController
) {


    val healthStat by newCharacterViewModel.healthStat.observeAsState(initial = -1.0)
    val damageStat by newCharacterViewModel.damageStat.observeAsState(initial = -1.0)
    val tearsStat by newCharacterViewModel.tearsStat.observeAsState(initial = -1.0)
    val shotSpeedStat by newCharacterViewModel.shotSpeedStat.observeAsState(initial = -1.0)
    val rangeStat by newCharacterViewModel.rangeStat.observeAsState(initial = -1.0)
    val luckStat by newCharacterViewModel.luckStat.observeAsState(initial = -1.0)
    val speedStat by newCharacterViewModel.speedStat.observeAsState(initial = -1.0)

    var showShotSpeedDialog by remember { mutableStateOf(false) }
    var showHealthDialog by remember { mutableStateOf(false) }
    var showDamageDialog by remember { mutableStateOf(false) }
    var showSpeedDialog by remember { mutableStateOf(false) }
    var showTearsDialog by remember { mutableStateOf(false) }
    var showRangeDialog by remember { mutableStateOf(false) }
    var showLuckDialog by remember { mutableStateOf(false) }
    var shotSpeedInput by rememberSaveable { mutableStateOf("") }


    var healthComplete by rememberSaveable { mutableStateOf(false) }
    var damageComplete by rememberSaveable { mutableStateOf(false) }
    var tearsComplete by rememberSaveable { mutableStateOf(false) }
    var shotSpeedComplete by rememberSaveable { mutableStateOf(false) }
    var rangeComplete by rememberSaveable { mutableStateOf(false) }
    var luckComplete by rememberSaveable { mutableStateOf(false) }
    var speedComplete by rememberSaveable { mutableStateOf(false) }
    var showErrorLastTest by rememberSaveable{ mutableStateOf(false) }
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
    var showInfo by rememberSaveable { mutableStateOf(false) }
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
    val lastImage by newCharacterViewModel.lastImage.observeAsState("")
    val name by newCharacterViewModel.newCharacterName.observeAsState(""
    )
    newCharacterViewModel.getLastImageUrlAppearance()
    val newcharacter = NewCharacter(
        custom = true, imageUrl = getRandomImageExcludingLast(
            lastImage
        ), name = name,
        tainted = false,
        transitionImage = transitionImageIsaac,
        unlockable = false, wayToUnlock = ""
    )


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.item_details)) },
                navigationIcon = {
                    androidx.compose.material3.IconButton(onClick = {
                        navController.popBackStack()

                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showInfo = !showInfo }) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(R.string.info)
                        )
                    }
                }
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
            if (!healthComplete || !speedComplete || !tearsComplete || !damageComplete || !rangeComplete || !shotSpeedComplete || !luckComplete) {
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


                    }) {
                        Text(stringResource(R.string.save_stat))
                    }
                }
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

            Spacer(Modifier.height(40.dp))
            if (healthComplete) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painterResource(statIcon[0].first),
                        contentDescription = stringResource(statIcon[0].second),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        stringResource(R.string.health) + healthStat.toString(),
                        style = MaterialTheme.typography.h5
                    )
                    IconButton(onClick = { showHealthDialog = !showHealthDialog }) {
                        Icon(
                            imageVector = Icons.Filled.Mode,
                            contentDescription = stringResource(R.string.modify),
                            Modifier.size(20.dp)
                        )
                    }
                }
                if (showHealthDialog) {
                    AlertDialog(
                        onDismissRequest = { showHealthDialog != showHealthDialog },
                        title = { Text("Modify Stat") },
                        text = {
                            Column {
                                StatTextField(
                                    statString = healthInput,
                                    onStatChange = {
                                        healthInput = it
                                    },
                                    pattern = healthRegex,
                                    example = R.string.example
                                )
                            }
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    newCharacterViewModel.setHealthStat(
                                        healthInput.toDoubleOrNull() ?: 1.0
                                    )
                                    showHealthDialog = !showHealthDialog
                                }
                            ) {
                                Text("OK")
                            }
                        }
                    )
                }
            }
            Spacer(Modifier.height(5.dp))
            if (speedComplete) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painterResource(statIcon[1].first),
                        contentDescription = stringResource(statIcon[1].second),
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        stringResource(R.string.speed) + speedStat.toString(),
                        style = MaterialTheme.typography.h5
                    )
                    IconButton(onClick = { showSpeedDialog = !showSpeedDialog }) {
                        Icon(
                            imageVector = Icons.Filled.Mode,
                            contentDescription = stringResource(R.string.modify),
                            Modifier.size(20.dp)
                        )
                    }
                }
                if (showSpeedDialog) {
                    AlertDialog(
                        onDismissRequest = { showSpeedDialog != showSpeedDialog },
                        title = { Text("Modify Stat") },
                        text = {
                            Column {
                                StatTextField(
                                    statString = speedInput,
                                    onStatChange = {
                                        speedInput = it
                                    },
                                    pattern = speedRegex,
                                    example = R.string.example
                                )
                            }
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    newCharacterViewModel.setSpeedStat(
                                        speedInput.toDoubleOrNull() ?: 1.0
                                    )
                                    showSpeedDialog = !showSpeedDialog
                                }
                            ) {
                                Text("OK")
                            }
                        }
                    )
                }
            }
            Spacer(Modifier.height(5.dp))
            if (tearsComplete) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painterResource(statIcon[2].first),
                        contentDescription = stringResource(statIcon[2].second),
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(Modifier.width(10.dp))

                    Text(
                        stringResource(R.string.tears) + tearsStat.toString(),
                        style = MaterialTheme.typography.h5
                    )
                    IconButton(onClick = { showTearsDialog = !showTearsDialog }) {
                        Icon(
                            imageVector = Icons.Filled.Mode,
                            contentDescription = stringResource(R.string.modify),
                            Modifier.size(20.dp)
                        )
                    }
                }
                if (showShotSpeedDialog) {
                    AlertDialog(
                        onDismissRequest = { showTearsDialog != showTearsDialog },
                        title = { Text("Modify Stat") },
                        text = {
                            Column {
                                StatTextField(
                                    statString = tearsInput,
                                    onStatChange = {
                                        tearsInput = it
                                    },
                                    pattern = tearsRegex,
                                    example = R.string.example
                                )
                            }
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    newCharacterViewModel.setTearsStat(
                                        tearsInput.toDoubleOrNull() ?: 1.0
                                    )
                                    showTearsDialog = !showTearsDialog
                                }
                            ) {
                                Text("OK")
                            }
                        }
                    )
                }
            }
            Spacer(Modifier.height(5.dp))
            if (damageComplete) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painterResource(statIcon[3].first),
                        contentDescription = stringResource(statIcon[4].second),
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        stringResource(R.string.damage) + damageStat.toString(),
                        style = MaterialTheme.typography.h5
                    )
                    IconButton(onClick = { showDamageDialog = !showDamageDialog }) {
                        Icon(
                            imageVector = Icons.Filled.Mode,
                            contentDescription = stringResource(R.string.modify),
                            Modifier.size(20.dp)
                        )
                    }
                }
                if (showDamageDialog) {
                    AlertDialog(
                        onDismissRequest = { showDamageDialog != showDamageDialog },
                        title = { Text("Modify Stat") },
                        text = {
                            Column {
                                StatTextField(
                                    statString = damageInput,
                                    onStatChange = {
                                        damageInput = it
                                    },
                                    pattern = damageRegex,
                                    example = R.string.example
                                )
                            }
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    newCharacterViewModel.setDamageStat(
                                        damageInput.toDoubleOrNull() ?: 1.0
                                    )
                                    showDamageDialog = !showDamageDialog
                                }
                            ) {
                                Text("OK")
                            }
                        }
                    )
                }
            }
            Spacer(Modifier.height(5.dp))
            if (rangeComplete) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painterResource(statIcon[4].first),
                        contentDescription = stringResource(statIcon[4].second),
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        stringResource(R.string.range) + rangeStat.toString(),
                        style = MaterialTheme.typography.h5
                    )
                    IconButton(onClick = { showRangeDialog = !showRangeDialog }) {
                        Icon(
                            imageVector = Icons.Filled.Mode,
                            contentDescription = stringResource(R.string.modify),
                            Modifier.size(20.dp)
                        )
                    }

                }
                if (showRangeDialog) {
                    AlertDialog(
                        onDismissRequest = { showRangeDialog != showRangeDialog },
                        title = { Text("Modify Stat") },
                        text = {
                            Column {
                                StatTextField(
                                    statString = rangeInput,
                                    onStatChange = {
                                        rangeInput = it
                                    },
                                    pattern = rangeRegex,
                                    example = R.string.example
                                )
                            }
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    newCharacterViewModel.setRangeStat(
                                        rangeInput.toDoubleOrNull() ?: 1.0
                                    )
                                    showRangeDialog = !showRangeDialog
                                }
                            ) {
                                Text("OK")
                            }
                        }
                    )
                }
            }
            Spacer(Modifier.height(5.dp))
            if (shotSpeedComplete) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painterResource(statIcon[5].first),
                        contentDescription = stringResource(statIcon[5].second),
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(Modifier.width(10.dp))

                    Text(
                        stringResource(R.string.shot_speed) + shotSpeedStat.toString(),
                        style = MaterialTheme.typography.h5
                    )
                    IconButton(onClick = { showShotSpeedDialog = !showShotSpeedDialog }) {
                        Icon(
                            imageVector = Icons.Filled.Mode,
                            contentDescription = stringResource(R.string.modify),
                            Modifier.size(20.dp)
                        )
                    }
                    if (showShotSpeedDialog) {
                        AlertDialog(
                            onDismissRequest = { showShotSpeedDialog != showShotSpeedDialog },
                            title = { Text("Modify Stat") },
                            text = {
                                Column {
                                    StatTextField(
                                        statString = shotSpeedInput,
                                        onStatChange = {
                                            shotSpeedInput = it
                                        },
                                        pattern = shotSpeedRegex,
                                        example = R.string.example
                                    )
                                }
                            },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        newCharacterViewModel.setShotSpeedStat(
                                            shotSpeedInput.toDoubleOrNull() ?: 1.0
                                        )
                                        showShotSpeedDialog = !showShotSpeedDialog
                                    }
                                ) {
                                    Text("OK")
                                }
                            }
                        )
                    }
                }
            }
            Spacer(Modifier.height(5.dp))
            if (luckComplete) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painterResource(statIcon[6].first),
                        contentDescription = stringResource(statIcon[6].second),
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        stringResource(R.string.luck) + luckStat.toString(),
                        style = MaterialTheme.typography.h5
                    )
                    IconButton(onClick = { showLuckDialog = !showLuckDialog }) {
                        Icon(
                            imageVector = Icons.Filled.Mode,
                            contentDescription = stringResource(R.string.modify),
                            Modifier.size(20.dp)
                        )
                    }
                }
                if (showLuckDialog) {
                    AlertDialog(
                        onDismissRequest = { showLuckDialog != showLuckDialog },
                        title = { Text("Modify Stat") },
                        text = {
                            Column {
                                StatTextField(
                                    statString = luckInput,
                                    onStatChange = {
                                        luckInput = it
                                    },
                                    pattern = shotSpeedRegex,
                                    example = R.string.example
                                )
                            }
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    newCharacterViewModel.setLuckStat(
                                        luckInput.toDoubleOrNull() ?: 1.0
                                    )
                                    showLuckDialog = !showLuckDialog
                                }
                            ) {
                                Text("OK")
                            }
                        }
                    )
                }


                Button(onClick = { showErrorLastTest = !showErrorLastTest }) {
                    Text(stringResource(R.string.create_character))
                }

            }
            if (showErrorLastTest){
                AlertDialog(
                    onDismissRequest = {showErrorLastTest = !showErrorLastTest },
                    title = {
                        Text(text = stringResource(R.string.lastDayError))
                    },
                    text = {
                        Text(text = stringResource(R.string.lastDayErrorText))
                    },
                    confirmButton = {
                        Button(
                            onClick = { showErrorLastTest = !showErrorLastTest },
                        ) {
                            Text(stringResource(R.string.exit))
                        }
                    }
                )
            }
            if (showInfo) {
                AlertDialog(
                    onDismissRequest = { showInfo = !showInfo },
                    {},
                    dismissButton = {
                        Button(onClick = {
                            showInfo = !showInfo
                        }) {
                            Text(stringResource(R.string.exit))
                        }
                    },
                    title = {
                        Text(stringResource(R.string.stats_info))
                    },
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.padding(horizontal = 8.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(stringResource(R.string.info_health_values))
                                Text(stringResource(R.string.info_damage_values))
                                Text(stringResource(R.string.info_speed_values))
                                Text(stringResource(R.string.info_range_values))
                                Text(stringResource(R.string.info_shot_speed_values))
                                Text(stringResource(R.string.info_luck_values))
                                Text(stringResource(R.string.info_tears_values))
                            }
                        }
                    }
                )
            }

        }
    }


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
fun getRandomImageExcludingLast(lastImage: String): String {
    val availableUrls = listImagesAppearances.filter { it != lastImage }
    return availableUrls.random()
}
