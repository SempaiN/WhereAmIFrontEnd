package com.ignacioperez.whereami.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ignacioperez.whereami.viewmodel.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

@Composable
fun TestAPI(userViewModel: UserViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        var searchedItem by rememberSaveable {
            mutableStateOf("")
        }
        var responseJSON by rememberSaveable {
            mutableStateOf("")
        }
        val coroutineScope = rememberCoroutineScope()
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(value = searchedItem, onValueChange = { searchedItem = it })
            Button(onClick = {
                coroutineScope.launch(Dispatchers.IO) {
                    val apiURL = "http://192.168.183.189:8080/tboi/items/"
                    val regParam = URLEncoder.encode(searchedItem, "UTF-8")
                    val endpoint = URL("$apiURL$regParam")
                    with(endpoint.openConnection() as HttpURLConnection) {
                        requestMethod = "GET"
                        Log.i("INFO", "URL: $url")
                        BufferedReader(InputStreamReader(inputStream)).use {
                            val response = StringBuffer()
                            var inputLine = it.readLine()
                            while (inputLine != null) {
                                response.append(inputLine)
                                inputLine = it.readLine()
                            }
                            it.close()
                            responseJSON = response.toString()
                        }
                    }
                }
            }) {
                Text(text = "Buscar")
            }
        }
        Text(
            text = responseJSON,
            modifier = Modifier
                .weight(8f)
                .verticalScroll(rememberScrollState())
        )


    }
}