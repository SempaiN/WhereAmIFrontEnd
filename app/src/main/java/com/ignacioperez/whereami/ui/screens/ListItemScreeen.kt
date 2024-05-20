package com.ignacioperez.whereami.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ignacioperez.whereami.models.Item
import com.ignacioperez.whereami.viewmodel.ItemViewModel

@Composable
fun ListItems(itemViewModel: ItemViewModel) {
    itemViewModel.getAllItems()
    val itemList: List<Item> by itemViewModel.allItems.observeAsState(initial = emptyList())
    val isLoading: Boolean by itemViewModel.isLoading.observeAsState(initial = false)

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(vertical = 8.dp),
        columns = GridCells.Adaptive(minSize = 128.dp)
    ) {
        items(
            itemList
        ) {
            ItemCard(item = it)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemCard(item: Item) {
    OutlinedCard(modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)) {
        ListItem(
            headlineContent = { Text(text = item.name) },

            )

    }
}