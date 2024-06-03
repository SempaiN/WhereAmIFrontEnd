import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ignacioperez.whereami.R
import com.ignacioperez.whereami.models.ObjectChangeStats
import com.ignacioperez.whereami.viewmodel.CardRuneViewModel
import com.ignacioperez.whereami.viewmodel.ItemViewModel
import com.ignacioperez.whereami.viewmodel.NewCharacterViewModel
import com.ignacioperez.whereami.viewmodel.PillViewModel
import com.ignacioperez.whereami.viewmodel.TrinketViewModel

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
    val statsChangedByItems by itemViewModel.statsChangedByItem.observeAsState(emptyList())
    val statsChangedByCardRune by cardRuneViewModel.statsChangedByCardRune.observeAsState(emptyList())
    val statsChangedByTrinket by trinketViewModel.statsChangedByTrinket.observeAsState(emptyList())
    val listStatsList by remember {
        mutableStateOf(MutableList(3) { emptyList<ObjectChangeStats>() })
    }


    LaunchedEffect(listItemsNewCharacter) {
        for (item in listItemsNewCharacter) {
            val stats = itemViewModel.loadStatsResponseObject(item.id)
            listStatsList.add(stats)
        }
    }

    LaunchedEffect(cardRuneNewCharacter?.id != -1) {
        cardRuneNewCharacter?.id?.let { cardRuneViewModel.loadStats(it) }
    }
    LaunchedEffect(trinketNewCharacter?.id != -1) {
        trinketNewCharacter?.let { trinketViewModel.loadStats(it.id) }
    }
    LaunchedEffect(pillNewCharacter?.id != -1) {
        pillNewCharacter?.let { pillViewModel.loadStats(it.id) }
    }

    val initialStats = listOf(
        ObjectChangeStats("Health", 0.0),
        ObjectChangeStats("Damage", 0.0),
        ObjectChangeStats("Tears", 0.0),
        ObjectChangeStats("Shot Speed", 0.0),
        ObjectChangeStats("Range", 0.0),
        ObjectChangeStats("Luck", 0.0),
        ObjectChangeStats("Speed", 0.0)
    )

    val updatedStats = remember(
        statsChangedByCardRune,
        statsChangedByTrinket,
        statsChangedByItems,
        listItemsNewCharacter,
        statsChangedByPill
    ) {
        val newStats = initialStats.map { it.copy() }.toMutableList()

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

        for (item in listStatsList) {

            for (statChange in item) {
                val stat = newStats.find { it.name == statChange.name }
                if (stat != null) {
                    stat.value += statChange.value
                }
            }
        }
        newStats
    }



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.item_details)) }
            )
        },
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(it),
        ) {
            Text(
                stringResource(R.string.character_will_start),
                style = MaterialTheme.typography.body1
            )
            for (stat in updatedStats) {
                val statInfo = statInfo[stat.name]
                Row {
                    Icon(
                        painter = painterResource(statInfo!!.first),
                        contentDescription = stringResource(statInfo.second),
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(text = stat.name + ": ", style = MaterialTheme.typography.body1)
                    Text(stat.value.toString())
                }
            }
        }
    }
}
