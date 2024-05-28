package com.ignacioperez.whereami.ui.screens.tabs.pickuptabas

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.rememberNavController
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.ignacioperez.whereami.R
import com.ignacioperez.whereami.models.ListCardRunes
import com.ignacioperez.whereami.mycomposables.CardRuneCard
import com.ignacioperez.whereami.viewmodel.CardRuneViewModel


object CardRuneTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = painterResource(id = R.drawable.facecard)
            return remember {
                TabOptions(
                    index = 1u,
                    title = "Cards/Runes",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val cardRuneViewModel = remember { CardRuneViewModel() }
        val navController = rememberNavController()
        cardRuneViewModel.getAllCardsRunes()
        val cardRuneList: ListCardRunes by cardRuneViewModel.allCardsRunes.observeAsState(
            initial = ListCardRunes()
        )

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(
                cardRuneList
            ) { cardRune ->
                CardRuneCard(
                    cardRune,
                    cardRuneViewModel,
                    navController = navController
                )
            }
        }
    }

}
