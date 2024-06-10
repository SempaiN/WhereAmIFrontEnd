package com.ignacioperez.whereami.ui.screens.loginsignin

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ignacioperez.whereami.R
import com.ignacioperez.whereami.checkAccountExistsFirebase
import com.ignacioperez.whereami.checkEmail
import com.ignacioperez.whereami.checkPassword
import com.ignacioperez.whereami.auth
import com.ignacioperez.whereami.models.User
import com.ignacioperez.whereami.mycomposables.PasswordTextField
import com.ignacioperez.whereami.viewmodel.RegisterViewModel
import com.ignacioperez.whereami.viewmodel.UserViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Register(
    navController: NavController,
    registerViewModel: RegisterViewModel,
    userViewModel: UserViewModel
) {

    var email by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current
    val buttonEnabled by remember {
        derivedStateOf {
            checkEmail(email) && checkPassword(password)
        }
    }
    var name by rememberSaveable {
        mutableStateOf("")
    }

    var coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.padding(16.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = stringResource(id = R.string.register),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            OutlinedTextField(value = name, onValueChange = { name = it }, label = {
                Text(text = stringResource(id = R.string.enter_name))
            }, modifier = Modifier.fillMaxWidth())

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
                    checkAccountExistsFirebase(email, password, auth) { exists ->
                        if (exists) {
                            Toast.makeText(
                                context,
                                context.getString(R.string.account_exists),
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener() {
                                    if (!it.isSuccessful) {
                                        Toast.makeText(
                                            context,
                                            context.getString(R.string.registration_failed),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        coroutineScope.launch {
                                            var last = registerViewModel.getLastUser()
                                            var newUser = User(
                                                id = last + 1,
                                                name = name,
                                                email = email
                                            )
                                            registerViewModel.createUser(newUser)
                                            Toast.makeText(
                                                context,
                                                context.getString(R.string.registration_successful),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            delay(3000)
                                            navController.navigate("LogInScreen")
                                        }
                                    }
                                }

                        }
                    }


                }, modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp), enabled = buttonEnabled
            ) {
                Text(stringResource(id = R.string.register))
            }
            Column(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .size(width = 300.dp, height = 150.dp)
                    .fillMaxWidth(0.5f)
            ) {
                Text(
                    text = stringResource(id = R.string.password_requirements),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 16.dp)
                )

                Text(
                    text = stringResource(id = R.string.minimum_length),
                    modifier = Modifier.padding(start = 16.dp)
                )
                Text(
                    text = stringResource(id = R.string.upper_letter),
                    modifier = Modifier.padding(start = 16.dp)
                )
                Text(
                    text = stringResource(id = R.string.lower_letter),
                    modifier = Modifier.padding(start = 16.dp)
                )
                Text(
                    text = stringResource(id = R.string.digit),
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

        }

    }

}

