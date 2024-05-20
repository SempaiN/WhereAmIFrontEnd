package com.ignacioperez.whereami

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.FirebaseApp
import com.ignacioperez.whereami.navigation.Navigation
import com.ignacioperez.whereami.ui.screens.Login
import com.ignacioperez.whereami.ui.screens.TestAPI
import com.ignacioperez.whereami.ui.theme.WhereAmITheme
import com.ignacioperez.whereami.viewmodel.UserViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userViewModel by viewModels<UserViewModel>()
        enableEdgeToEdge()
        setContent {
            WhereAmITheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(userViewModel)
                }
            }
        }
    }
}

