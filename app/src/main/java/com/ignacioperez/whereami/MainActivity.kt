package com.ignacioperez.whereami

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.ignacioperez.whereami.navigation.Navigation
import com.ignacioperez.whereami.ui.theme.WhereAmITheme
import com.ignacioperez.whereami.viewmodel.CardRuneViewModel
import com.ignacioperez.whereami.viewmodel.CharacterViewModel
import com.ignacioperez.whereami.viewmodel.ItemViewModel
import com.ignacioperez.whereami.viewmodel.NewCharacterViewModel
import com.ignacioperez.whereami.viewmodel.PillViewModel
import com.ignacioperez.whereami.viewmodel.UserViewModel
import com.ignacioperez.whereami.viewmodel.TrinketViewModel
import com.ignacioperez.whereami.viewmodel.RegisterViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val registerViewModel by viewModels<RegisterViewModel>()
        val itemViewModel by viewModels<ItemViewModel>()
        val userViewModel by viewModels<UserViewModel>()
        val characterViewModel by viewModels<CharacterViewModel>()
        val trinketViewModel by viewModels<TrinketViewModel>()
        val cardRuneViewModel by viewModels<CardRuneViewModel>()
        val newCardRuneViewModel by viewModels<NewCharacterViewModel>()
        val pillViewModel by viewModels<PillViewModel>()
        enableEdgeToEdge()
        setContent {
            WhereAmITheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(
                        registerViewModel,
                        userViewModel,
                        itemViewModel,
                        characterViewModel,
                        trinketViewModel,
                        cardRuneViewModel,
                        newCardRuneViewModel,
                        pillViewModel
                    )
                }
            }
        }
    }
}

