package com.ignacioperez.whereami.mycomposables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.ignacioperez.whereami.R

@Composable
fun PasswordTextField(
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibleChange: (Boolean) -> Unit,
){
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
            IconButton(onClick = {  onPasswordVisibleChange(!passwordVisible) }) {
                Icon(imageVector = image, description)
            }
        },
        modifier = Modifier.fillMaxWidth()

    )
}