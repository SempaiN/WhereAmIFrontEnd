package com.ignacioperez.whereami.ui.screens

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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.auth
import com.ignacioperez.whereami.R
import com.ignacioperez.whereami.auth
import com.ignacioperez.whereami.models.User
import com.ignacioperez.whereami.mycomposables.PasswordTextField
import com.ignacioperez.whereami.viewmodel.SignInViewModel


@Composable
fun Login(navController: NavController, signInViewModel: SignInViewModel) {
    var email by rememberSaveable {
        mutableStateOf("trollnacho.np@gmail.com")
    }
    var password by rememberSaveable {
        mutableStateOf("Pene3500")
    }
    var passwordVisible by rememberSaveable {
        mutableStateOf(false)
    }
    val user by signInViewModel.user.observeAsState(
        initial = User()
    )
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
                            Log.i("User", user.user.toString() + "" + email)
                            signInViewModel.getUserFromDB(email)
                            navController.navigate("HomeScreen")
                        }
                    }
                }, modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
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
        }
    }

}

