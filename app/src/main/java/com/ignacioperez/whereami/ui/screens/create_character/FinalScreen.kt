package com.ignacioperez.whereami.ui.screens.create_character

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ignacioperez.whereami.R
import com.ignacioperez.whereami.models.Stat
import com.ignacioperez.whereami.viewmodel.CardRuneViewModel
import com.ignacioperez.whereami.viewmodel.ItemViewModel
import com.ignacioperez.whereami.viewmodel.NewCharacterViewModel
import com.ignacioperez.whereami.viewmodel.PillViewModel
import com.ignacioperez.whereami.viewmodel.TrinketViewModel
import com.ignacioperez.whereami.viewmodel.UserViewModel

@Composable
fun FinalScreen(
    newCharacterViewModel: NewCharacterViewModel,
    userViewModel: UserViewModel,
    pillViewModel: PillViewModel,
    cardRuneViewModel: CardRuneViewModel,
    trinketViewModel: TrinketViewModel,
    itemViewModel: ItemViewModel
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

    val listStatsCharacter = mutableListOf(
        healthStat,
        damageStat,
        tearsStat,
        shotSpeedStat,
        rangeStat,
        luckStat,
        speedStat
    )
    val initialStats = mutableListOf(
        Stat("Health", 0.0),
        Stat("Damage", 0.0),
        Stat("Tears", 0.0),
        Stat("Shot Speed", 0.0),
        Stat("Range", 0.0),
        Stat("Luck", 0.0),
        Stat("Speed", 0.0)
    )
    val statInfo = listOf(
        "Health" to Pair(R.drawable.health_stat_icon, R.string.health_stat),
        "Speed" to Pair(R.drawable.speed_stat_icon, R.string.speed_stat),
        "Tears" to Pair(R.drawable.tears_stat_icon, R.string.tears_stat),
        "Damage" to Pair(R.drawable.damage_stat_icon, R.string.damage_stat),
        "Range" to Pair(R.drawable.range_stat_icon, R.string.range_stat),
        "Shot Speed" to Pair(R.drawable.shot_speed_stat_icon, R.string.shot_speed_stat),
        "Luck" to Pair(R.drawable.luck_stat_icon, R.string.luck_stat)
    )
    if (listItemsNewCharacter.isEmpty() && trinketNewCharacter == null && cardRuneNewCharacter == null && pillNewCharacter == null) {
        // Handle empty state
    } else {
        Text(
            stringResource(R.string.character_will_start),
            style = MaterialTheme.typography.body1
        )

    }
    LaunchedEffect(listItemsNewCharacter) {
        val newListStats = mutableListOf<Stat>()
        for (item in listItemsNewCharacter) {
            newListStats.addAll(itemViewModel.loadStatsResponseObject(item.id))
        }
        listStatsList.value = newListStats
    }

    LaunchedEffect(cardRuneNewCharacter?.id) {
        cardRuneNewCharacter?.id?.let { cardRuneViewModel.loadStats(it) }
    }
    LaunchedEffect(trinketNewCharacter?.id) {
        trinketNewCharacter?.let { trinketViewModel.loadStats(it.id) }
    }
    LaunchedEffect(pillNewCharacter?.id) {
        pillNewCharacter?.let { pillViewModel.loadStats(it.id) }
    }

    val updatedStats = remember(
        statsChangedByCardRune,
        statsChangedByTrinket,
        statsChangedByPill,
        listStatsList.value
    ) {
        val newStats = initialStats.toMutableList()

        for (statCard in statsChangedByCardRune) {
            val stat = newStats.find { it.name == statCard.name }
            if (stat != null) {
                stat.value += statCard.value
            }
        }

        for (statTrinket in statsChangedByTrinket) {
            val stat = newStats.find { it.name == statTrinket.name }
            if (stat != null) {
                stat.value += statTrinket.value
            }
        }

        for (statPill in statsChangedByPill) {
            val stat = newStats.find { it.name == statPill.name }
            if (stat != null) {
                stat.value += statPill.value
            }
        }

        for (itemStat in listStatsList.value) {
            val stat = newStats.find { it.name == itemStat.name }
            if (stat != null) {
                stat.value += itemStat.value
            }
        }
        newStats
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(stringResource(R.string.final_stats))
        for (index in listStatsCharacter.indices) {
            val finalStat = listStatsCharacter[index] + updatedStats[index].value
            Text(finalStat.toString())
        }
        Spacer(Modifier.height(20.dp))
        for (update in updatedStats) {
            Text(update.value.toString())
        }
    }
}


// Main function to generate stats based on rules