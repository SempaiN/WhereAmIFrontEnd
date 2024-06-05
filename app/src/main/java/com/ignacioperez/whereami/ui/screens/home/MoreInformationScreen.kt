package com.ignacioperez.whereami.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ignacioperez.whereami.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreInformationScreen(navController: NavController) {
    var scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.more_information)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.home_screen)
                        )
                    }
                })
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .verticalScroll(scrollState)
        ) {
            Text(
                text = stringResource(R.string.attributes),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                )
            Divider(color = Color.Black)
            InformationStat(R.string.health_stat, R.drawable.health_stat_icon)
            InformationStat(R.string.speed_stat, R.drawable.speed_stat_icon)
            InformationStat(R.string.tears_stat, R.drawable.tears_stat_icon)
            InformationStat(R.string.damage_stat, R.drawable.damage_stat_icon)
            InformationStat(R.string.range_stat, R.drawable.range_stat_icon)
            InformationStat(R.string.shot_speed_stat, R.drawable.shot_speed_stat_icon)
            InformationStat(R.string.luck_stat, R.drawable.luck_stat_icon)
            Spacer(modifier = Modifier.size(20.dp))
            Text(
                text = stringResource(R.string.pickups),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
            Divider(color = Color.Black)
            Information(R.string.pickups_info)
            Information(R.string.chests_info)
            Information(R.string.pills_info)
            Information(R.string.cards_rune_info)
        }
    }

}

@Composable
private fun InformationStat(statExplanation: Int, iconStat: Int) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),

        ) {
        Text(
            text = "\u2022",
            modifier = Modifier.padding(end = 10.dp),
            fontSize = 25.sp
        )
        Icon(
            painter = painterResource(iconStat),
            contentDescription = stringResource(R.string.health),
            modifier = Modifier.size(25.dp)
        )
        Spacer(modifier = Modifier.size(width = 8.dp, height = 0.dp))
        Text(text = stringResource(statExplanation), style = MaterialTheme.typography.titleMedium)
    }
}