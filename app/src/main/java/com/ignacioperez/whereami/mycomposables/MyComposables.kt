package com.ignacioperez.whereami.mycomposables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ignacioperez.whereami.R
import com.ignacioperez.whereami.models.CardRune
import com.ignacioperez.whereami.models.CharacterResponse
import com.ignacioperez.whereami.models.ObjectChangeStatsList
import com.ignacioperez.whereami.models.Pill
import com.ignacioperez.whereami.navigation.Routes
import com.ignacioperez.whereami.viewmodel.CardRuneViewModel
import com.ignacioperez.whereami.viewmodel.CharacterViewModel
import com.ignacioperez.whereami.viewmodel.NewCharacterViewModel
import com.ignacioperez.whereami.viewmodel.PillViewModel
import java.time.format.TextStyle


@Composable
fun StatTextField(
    statString: String,
    onStatChange: (String) -> Unit,
    pattern: Regex,
    example: Int
) {
    OutlinedTextField(
        value = statString,
        onValueChange = {
            if (it.isEmpty() || it.matches(pattern)) {
                onStatChange(it)
            }
        },
        label = {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(example))
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier
            .size(width = 90.dp, height = 65.dp)


    )
}

@Composable
fun PasswordTextField(
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibleChange: (Boolean) -> Unit,
) {
    OutlinedTextField(
        value = password,
        onValueChange = run { { onPasswordChange(it) } },
        label = { Text(text = stringResource(id = R.string.introduce_password)) },
        singleLine = true,
        placeholder = { Text(text = stringResource(id = R.string.introduce_password)) },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisible)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff
            val description = if (passwordVisible) "Hide password" else "Show password"
            IconButton(onClick = { onPasswordVisibleChange(!passwordVisible) }) {
                Icon(imageVector = image, description)
            }
        },
        modifier = Modifier.fillMaxWidth()

    )
}

@Composable
fun ModifyStatSelected(
    newCharacterViewModel: NewCharacterViewModel,
    show: Boolean,
    onDismissRequest: (Boolean) -> Unit,
    currentStat: Double,
    pattern: Regex,
    example: Int,
) {
    var input by rememberSaveable { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismissRequest(false) },
        title = { Text("Modify Stat") },
        text = {
            Column {
                StatTextField(
                    statString = input,
                    onStatChange = { input = it },
                    pattern = pattern,
                    example = example
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {

                    onDismissRequest(false)
                }
            ) {
                Text("OK")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarExit(title: Int, navController: NavController) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(
                    id = title,

                    )
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back)
                )
            }
        })
}

@Composable
fun ObjectStatsChanged(objectChangeStatsList: ObjectChangeStatsList) {
    val statInfo = mapOf(
        "Health" to Pair(R.drawable.health_stat_icon, R.string.health_stat),
        "Speed" to Pair(R.drawable.speed_stat_icon, R.string.speed_stat),
        "Tears" to Pair(R.drawable.tears_stat_icon, R.string.tears_stat),
        "Damage" to Pair(R.drawable.damage_stat_icon, R.string.damage_stat),
        "Range" to Pair(R.drawable.range_stat_icon, R.string.range_stat),
        "Shot Speed" to Pair(R.drawable.shot_speed_stat_icon, R.string.shot_speed_stat),
        "Luck" to Pair(R.drawable.luck_stat_icon, R.string.luck_stat)
    )
    for (stat in objectChangeStatsList) {
        val statInfo = statInfo[stat.name]
        Row() {
            Icon(
                painter = painterResource(statInfo!!.first),
                contentDescription = stringResource(statInfo.second),
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = stat.name + ": ", style = MaterialTheme.typography.titleSmall)
            Text(
                text = (if (stat.value > 0) "+" else "") + stat.value.toString(),
                color = if (stat.value > 0) Color.Green else Color.Red,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

@Composable
fun CardRuneCard(
    cardRune: CardRune,
    cardRuneViewModel: CardRuneViewModel,
    favorite: Boolean
) {
    val rainbowColorsBrush = remember {
        Brush.sweepGradient(
            listOf(
                Color(0xFF9575CD),
                Color(0xFFBA68C8),
                Color(0xFFE57373),
                Color(0xFFFFB74D),
                Color(0xFFFFF176),
                Color(0xFFAED581),
                Color(0xFF4DD0E1),
                Color(0xFF9575CD)
            )
        )
    }
    val borderWidth = 1.5.dp
    OutlinedCard(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable {
                cardRuneViewModel.onCardRuneClicked(cardRune)
            }
            .border(
                if (favorite) {
                    BorderStroke(borderWidth, rainbowColorsBrush)
                } else {
                    BorderStroke(1.5.dp, Color.Black)
                }, shape = CardDefaults.outlinedShape
            )
    ) {
        ListItem(
            headlineContent = {
                Text(cardRune.name, style = MaterialTheme.typography.titleLarge)
            },
            leadingContent = {
                AsyncImage(
                    model = cardRune.imageUrl,
                    placeholder = painterResource(R.drawable.facecard),
                    error = painterResource(R.drawable.facecard),
                    contentDescription = cardRune.name,
                    modifier = Modifier.size(40.dp)
                )
            }
        )
    }
}

@Composable
fun PillCard(
    pill: Pill,
    pillViewModel: PillViewModel,
    favorite: Boolean
) {
    val rainbowColorsBrush = remember {
        Brush.sweepGradient(
            listOf(
                Color(0xFF9575CD),
                Color(0xFFBA68C8),
                Color(0xFFE57373),
                Color(0xFFFFB74D),
                Color(0xFFFFF176),
                Color(0xFFAED581),
                Color(0xFF4DD0E1),
                Color(0xFF9575CD)
            )
        )
    }
    val borderWidth = 1.5.dp
    OutlinedCard(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable {
                pillViewModel.onPillClicked(pill)

            }
            .border(
                if (favorite) {
                    BorderStroke(borderWidth, rainbowColorsBrush)
                } else {
                    BorderStroke(1.5.dp, Color.Black)
                }, shape = CardDefaults.outlinedShape
            )

    ) {
        ListItem(
            headlineContent = {
                Text(pill.name, style = MaterialTheme.typography.titleLarge)
            },
            leadingContent = {
                AsyncImage(
                    model = pill.imageUrl,
                    placeholder = painterResource(R.drawable.facecard),
                    error = painterResource(R.drawable.facecard),
                    contentDescription = pill.name,
                    modifier = Modifier.size(40.dp)
                )
            }
        )
    }
}

@Composable
fun CharacterCard(
    character: CharacterResponse,
    characterViewModel: CharacterViewModel,
    navController: NavController
) {

    OutlinedCard(
        modifier = Modifier
            .padding(vertical = 7.dp, horizontal = 8.dp)
            .clickable {
                characterViewModel.onCharacterClicked(character = character)
                navController.navigate(route = Routes.CharacterDetailsScreen.route)
            }
            .size(width = 40.dp, height = 150.dp),

        ) {
        Column(
            modifier = Modifier.padding(start = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            AsyncImage(
                character.imageUrl,
                contentDescription = character.name,
                modifier = if (character.custom) Modifier.size(120.dp) else Modifier.size(100.dp)
            )
            Text(
                text = character.name,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}