package com.ignacioperez.whereami.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ignacioperez.whereami.R
import com.ignacioperez.whereami.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectItemScreen() {
    var select = 0
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = stringResource(
                    id = R.string.item_details
                )
            )
        })
        /////////AÑADIR UN BOTÓN QUE AÑADA EL ITEM, UN MÁXIMO DE 3 ITEMS
    }) {
        Column(
            Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Text(
                stringResource(R.string.how_many_items),
                style = MaterialTheme.typography.h5,
                textAlign = TextAlign.Center
            )
            select = NumberSelector()
            Text(select.toString())
        }
    }

}

@Composable
fun NumberSelector(): Int {
    var expanded by remember { mutableStateOf(false) }
    var selectedNumber by remember { mutableStateOf(0) }
    val numbers = listOf(0, 1, 2, 3)

    Row(
        modifier = Modifier.padding(16.dp).border(1.dp, Color.Black),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .clickable { expanded = true }
                .background(MaterialTheme.colors.surface)
                .padding(16.dp)

                .size(40.dp)
        ) {
            Text(text = selectedNumber.toString(), textAlign = TextAlign.Center)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            numbers.forEach { number ->
                DropdownMenuItem(onClick = {
                    selectedNumber = number
                    expanded = false
                }) {
                    Text(text = number.toString(), textAlign = TextAlign.Center)
                }
            }
        }
    }
    return selectedNumber
}