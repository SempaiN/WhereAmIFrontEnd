package com.ignacioperez.whereami.ui.screens.loginsignin

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.navigation.NavController
import com.ignacioperez.whereami.R
import com.ignacioperez.whereami.auth
import com.ignacioperez.whereami.isOnline
import com.ignacioperez.whereami.mycomposables.PasswordTextField
import com.ignacioperez.whereami.viewmodel.UserViewModel
import kotlin.system.exitProcess


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(navController: NavController, userViewModel: UserViewModel) {
    var email by rememberSaveable {
        mutableStateOf("trollnacho.np@gmail.com")
    }
    var password by rememberSaveable {
        mutableStateOf("Pene3500")
    }
    var passwordVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var showErrorLogIn by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier.padding(16.dp)
    ) { it ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.login),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(stringResource(id = R.string.introduce_email)) },
                modifier = Modifier.fillMaxWidth()
            )
            PasswordTextField(password = password,
                onPasswordChange = { password = it },
                passwordVisible = passwordVisible,
                onPasswordVisibleChange = { passwordVisible = it })
            Button(
                onClick = {
                    auth.signInWithEmailAndPassword(email, password).addOnSuccessListener { user ->
                        if (user.user != null) {
                            userViewModel.getUserFromDB(email)
                            navController.navigate("HomeScreen")
                        } else {
                            showErrorLogIn = true
                        }
                    }
                }, modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = isOnline(context)
            ) {
                Text(text = stringResource(id = R.string.enter))
            }
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = stringResource(id = R.string.dont_have))
                Spacer(modifier = Modifier.size(2.dp))
                Text(
                    text = stringResource(id = R.string.register_here),
                    modifier = Modifier.clickable {
                        navController.navigate("RegisterScreen")
                    },
                    textDecoration = TextDecoration.Underline

                )
            }
            if (!isOnline(context = context)) {
                AlertDialog(
                    onDismissRequest = { exitProcess(-1) },
                    title = {
                        Text(text = "No Internet Connection")
                    },
                    text = {
                        Text(text = stringResource(R.string.internet_failed))
                    },
                    confirmButton = {
                        Button(onClick = { exitProcess(-1) }) {
                            Text(text = stringResource(id = R.string.exit))
                        }
                    }
                )
            }
        }
    }

}

