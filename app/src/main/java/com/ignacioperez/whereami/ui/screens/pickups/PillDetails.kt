package com.ignacioperez.whereami.ui.screens.pickups

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ignacioperez.whereami.R
import com.ignacioperez.whereami.models.ObjectChangeStatsList
import com.ignacioperez.whereami.models.Pill
import com.ignacioperez.whereami.models.User
import com.ignacioperez.whereami.mycomposables.ObjectStatsChanged
import com.ignacioperez.whereami.viewmodel.PillViewModel
import com.ignacioperez.whereami.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PillDetails(pillViewModel: PillViewModel, userViewModel: UserViewModel) {
    val pill: Pill by pillViewModel.selectedPill.observeAsState(Pill())
    val user: User by userViewModel.user.observeAsState(User())
    val stats: ObjectChangeStatsList by pillViewModel.statsChangedByPill.observeAsState(
        ObjectChangeStatsList()
    )
    val showDialog: Boolean by pillViewModel.showPillDetails.observeAsState(false)
    var showSpoiler by rememberSaveable { mutableStateOf(false) }
    val isFavoritePill: Boolean by pillViewModel.isSelectedPillFavorite.observeAsState(false)
    pillViewModel.checkPillFavorite(pill, user)
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { pillViewModel.hidePillAlertDialog() },
            confirmButton = {
                if (pill.unlockable) {
                    TextButton(onClick = { showSpoiler = !showSpoiler }) {
                        Text(stringResource(R.string.show_spoiler))
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    pillViewModel.hidePillAlertDialog()
                    pillViewModel.clearSelectedPill()
                }) {
                    Text(stringResource(R.string.exit))
                }
                TextButton(onClick = {

                    if (isFavoritePill) {
                        pillViewModel.deletePillFavorite(pill, user, userViewModel)

                    } else {
                        pillViewModel.insertPillFavorite(pill, user, userViewModel)

                    }
                }) {
                    if (isFavoritePill) {
                        Text(stringResource(R.string.delete_favorite))
                    } else {
                        Text(stringResource(R.string.add_favorite))
                    }
                }
            },
            text = {
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .wrapContentWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = pill.imageUrl,
                            contentDescription = pill.name,
                            modifier = Modifier.size(50.dp)
                        )
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(top = 18.dp)
                        ) {
                            Text(
                                text = pill.name,
                                style = MaterialTheme.typography.headlineSmall,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Column(
                        modifier = Modifier
                            .border(1.dp, MaterialTheme.colorScheme.onSurface)
                            .padding(16.dp)
                            .wrapContentHeight()
                            .wrapContentWidth()
                    ) {
                        Text(
                            text = stringResource(R.string.effect) + pill.effect,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Row {
                            if (pill.unlockable) {
                                Text(
                                    text = stringResource(R.string.way_to_unlock)
                                )
                                Text(
                                    text = if (showSpoiler) pill.wayToUnlock else stringResource(R.string.secret)
                                )
                            }
                            ObjectStatsChanged(stats)
                        }
                    }
                }
            },
            modifier = Modifier
                .padding(16.dp)
                .wrapContentWidth()
                .wrapContentHeight()
        )
    }
}
