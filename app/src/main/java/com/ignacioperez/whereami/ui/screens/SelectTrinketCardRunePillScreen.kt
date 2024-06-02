package com.ignacioperez.whereami.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
fun SelectTrinketCardRunePillScreen() {

    val selectPill by rememberSaveable{
        mutableStateOf(false)
    }
    val selectCardRune by rememberSaveable{
        mutableStateOf(false)
    }


}