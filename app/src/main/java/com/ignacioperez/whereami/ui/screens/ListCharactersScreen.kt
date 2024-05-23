package com.ignacioperez.whereami.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ignacioperez.whereami.models.CharacterResponse
import com.ignacioperez.whereami.viewmodel.CharacterViewModel

@Composable
fun ListCharacters(characterViewModel: CharacterViewModel) {
    characterViewModel.loadCharacters()
    val characterList: List<CharacterResponse> by characterViewModel.defaultCharacters.observeAsState(
        initial = emptyList()
    )
    Scaffold() {
        LazyColumn(
            modifier = Modifier.padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            items(
                characterList
            ) { character ->
                CharacterCard(character)
            }
        }
    }
}

@Composable
fun CharacterCard(character: CharacterResponse) {
    OutlinedCard(
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Column(modifier = Modifier.border(1.dp, color = Color.Black)) {
            AsyncImage(character.imageUrl, contentDescription = character.name)
        }
    }
}