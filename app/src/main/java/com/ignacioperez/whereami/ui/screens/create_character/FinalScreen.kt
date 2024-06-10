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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    val listItemsNewCharacter by newCharacterViewModel.listItemsNewCharacter.observeAsState(emptyList())
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
        speedStat,
        tearsStat,
        damageStat,
        rangeStat,
        shotSpeedStat,
        luckStat,
    )
    val initialStats = mutableListOf(
        Stat("Health", 0.0),
        Stat("Speed", 0.0),
        Stat("Tears", 0.0),
        Stat("Damage", 0.0),
        Stat("Range", 0.0),
        Stat("Shot Speed", 0.0),
        Stat("Luck", 0.0)
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

    val finalStats = listStatsCharacter.mapIndexed { index, baseStat ->
        baseStat + updatedStats[index].value
    }

    // Use a mutable state to store the final stats to avoid infinite updates
    var finalStatsState by rememberSaveable { mutableStateOf(finalStats) }

    LaunchedEffect(finalStats) {
        if (finalStatsState != finalStats) {
            finalStatsState = finalStats
            newCharacterViewModel.setHealthStat(finalStats[0])
            newCharacterViewModel.setSpeedStat(finalStats[1])
            newCharacterViewModel.setTearsStat(finalStats[2])
            newCharacterViewModel.setDamageStat(finalStats[3])
            newCharacterViewModel.setRangeStat(finalStats[4])
            newCharacterViewModel.setShotSpeedStat(finalStats[5])
            newCharacterViewModel.setLuckStat(finalStats[6])
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(stringResource(R.string.final_stats))
        listStatsCharacter.forEach { stat ->
            Text(stat.toString())
        }
        Spacer(Modifier.height(20.dp))
    }
}
