package com.ignacioperez.whereami.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ignacioperez.whereami.R
import com.ignacioperez.whereami.models.CharacterResponse
import com.ignacioperez.whereami.models.Item
import com.ignacioperez.whereami.models.StatResponse
import com.ignacioperez.whereami.models.StatsModifiedCharacter
import com.ignacioperez.whereami.viewmodel.CharacterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetails(navController: NavController, characterViewModel: CharacterViewModel) {
    val character: CharacterResponse by characterViewModel.selectedCharacter.observeAsState(
        CharacterResponse()
    )
    val statsBase: StatResponse by characterViewModel.statsBaseCharacter.observeAsState(
        StatResponse()
    )
    val statsModified: StatsModifiedCharacter by characterViewModel.statsModified.observeAsState(
        StatsModifiedCharacter()
    )
    val itemsCharacter: List<Item> by characterViewModel.itemsCharacter.observeAsState(
        emptyList()
    )
    var showSpoiler by rememberSaveable() { mutableStateOf(false) }


    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = stringResource(
                    id = R.string.item_details
                )
            )
        }, navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()

            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back)
                )
            }
        }, actions = {
            if (character.unlockable) {
                Switch(checked = showSpoiler, onCheckedChange = { showSpoiler = it })
            }
        })
    }) {
        Column(
            modifier = Modifier.padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = character.imageUrl,
                    placeholder = painterResource(R.drawable.isaac_icon),
                    error = painterResource(R.drawable.isaac_icon),
                    contentDescription = character.name,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(start = 25.dp, top = 3.5.dp)

                )
                Divider()
                AsyncImage(
                    model = character.transitionImage,
                    placeholder = painterResource(R.drawable.isaac_icon),
                    error = painterResource(R.drawable.isaac_icon),
                    contentDescription = character.name,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(start = 25.dp, top = 3.5.dp)

                )
            }
        }
    }

}