package com.ignacioperez.whereami.ui.screens.pickups

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ignacioperez.whereami.R
import com.ignacioperez.whereami.models.CardRune
import com.ignacioperez.whereami.models.ObjectChangeStatsList
import com.ignacioperez.whereami.models.User
import com.ignacioperez.whereami.mycomposables.ObjectStatsChanged
import com.ignacioperez.whereami.viewmodel.CardRuneViewModel
import com.ignacioperez.whereami.viewmodel.UserViewModel

@Composable
fun CardRuneDetails(
    cardRuneViewModel: CardRuneViewModel, userViewModel: UserViewModel
) {
    val user: User by userViewModel.user.observeAsState(
        initial = User()
    )
    val cardRune: CardRune by cardRuneViewModel.selectedCardRune.observeAsState(CardRune())
    val stats: ObjectChangeStatsList by cardRuneViewModel.statsChangedByCardRune.observeAsState(
        ObjectChangeStatsList()
    )
    val isFavoriteCardRune by cardRuneViewModel.isCardRuneSelectedFavorite.observeAsState(false)
    cardRuneViewModel.checkCardRuneFavorite(cardRune, user)
    val showDialog: Boolean by cardRuneViewModel.showCardRuneDetails.observeAsState(false)
    var showSpoiler by rememberSaveable { mutableStateOf(false) }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { cardRuneViewModel.hideCardRuneDetailsAlertDialog() },
            confirmButton = {
                if (cardRune.unlockable) {
                    TextButton(onClick = { showSpoiler = !showSpoiler }) {
                        Text(stringResource(R.string.show_spoiler))
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    cardRuneViewModel.hideCardRuneDetailsAlertDialog()
                    cardRuneViewModel.clearSelectedCharacter()
                }) {
                    Text(stringResource(R.string.exit))
                }
                TextButton(onClick = {

                    if (isFavoriteCardRune) {
                        cardRuneViewModel.deleteCardRuneFavorite(cardRune, user, userViewModel)

                    } else {
                        cardRuneViewModel.insertCardRuneFavorite(cardRune, user, userViewModel)

                    }
                }) {
                    if (isFavoriteCardRune) {
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
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.wrapContentWidth()
                    ) {
                        AsyncImage(
                            model = cardRune.imageUrl,
                            contentDescription = cardRune.name,
                            modifier = Modifier.size(90.dp)
                        )
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(top = 18.dp)
                                .wrapContentWidth()
                        ) {
                            Row() {
                                Text(
                                    text = cardRune.name,
                                    style = MaterialTheme.typography.headlineSmall,
                                    textAlign = TextAlign.Center
                                )

                            }
                            Text(
                                text = cardRune.message,
                                style = MaterialTheme.typography.titleMedium,
                                fontStyle = FontStyle.Italic,
                                textAlign = TextAlign.Center,
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
                            text = stringResource(R.string.effect) + cardRune.description,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Row {
                            if (cardRune.unlockable) {
                                Text(
                                    text = stringResource(
                                        R.string.way_to_unlock
                                    )
                                )
                                Text(
                                    text = if (showSpoiler) cardRune.wayToUnlock else stringResource(
                                        R.string.secret
                                    )
                                )
                            }
                        }
                        ObjectStatsChanged(stats)
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
