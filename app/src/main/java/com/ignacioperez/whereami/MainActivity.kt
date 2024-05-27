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
import com.ignacioperez.whereami.viewmodel.CharacterViewModel
import com.ignacioperez.whereami.viewmodel.ItemViewModel
import com.ignacioperez.whereami.viewmodel.SignInViewModel
import com.ignacioperez.whereami.viewmodel.TrinketViewModel
import com.ignacioperez.whereami.viewmodel.UserViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userViewModel by viewModels<UserViewModel>()
        val itemViewModel by viewModels<ItemViewModel>()
        val signInViewModel by viewModels<SignInViewModel>()
        val characterViewModel by viewModels<CharacterViewModel>()
        val trinketViewModel by viewModels<TrinketViewModel>()
        enableEdgeToEdge()
        setContent {
            WhereAmITheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(
                        userViewModel,
                        signInViewModel,
                        itemViewModel,
                        characterViewModel,
                        trinketViewModel
                    )
                }
            }
        }
    }
}

