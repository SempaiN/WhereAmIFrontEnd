package com.ignacioperez.whereami.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ignacioperez.whereami.R
import com.ignacioperez.whereami.models.CardRune
import com.ignacioperez.whereami.models.Pill
import com.ignacioperez.whereami.models.Trinket
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
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.item_details)) }
            )
        }
    ) {
        Column(modifier = Modifier.padding(it)) {

        }
    }
}