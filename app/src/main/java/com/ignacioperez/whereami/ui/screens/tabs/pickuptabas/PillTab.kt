package com.ignacioperez.whereami.ui.screens.tabs.pickuptabas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.ignacioperez.whereami.R
import com.ignacioperez.whereami.models.ListPills
import com.ignacioperez.whereami.mycomposables.PillCard
import com.ignacioperez.whereami.viewmodel.PillViewModel

object PillTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = painterResource(R.drawable.pill_black_white)
            return remember {
                TabOptions(
                    index = 2u,
                    title = "Pills",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val pillViewModel = remember { PillViewModel() }
        val navController = rememberNavController()
        pillViewModel.getAllPills()
        val pillList: ListPills by pillViewModel.allPills.observeAsState(
            ListPills()
        )
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(
                pillList
            ) { pill ->
                PillCard(pill, pillViewModel, navController)
            }
        }
    }
}