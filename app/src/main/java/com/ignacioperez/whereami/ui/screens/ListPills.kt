package com.ignacioperez.whereami.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.ignacioperez.whereami.models.ListPills
import com.ignacioperez.whereami.mycomposables.PillCard
import com.ignacioperez.whereami.viewmodel.PillViewModel

@Composable
fun ListPills(pillViewModel: PillViewModel) {

    pillViewModel.getAllPills()
    val pillList: ListPills by pillViewModel.allPills.observeAsState(
        ListPills()
    )
    val showDialog: Boolean by pillViewModel.showPillDetails.observeAsState(
        false
    )
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(
            pillList
        ) { pill ->
            PillCard(pill, pillViewModel)
        }
    }
    if (showDialog) {
        PillDetails(pillViewModel)
    }

}