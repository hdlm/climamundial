package com.example.climamundial.ui

import android.util.Log
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.climamundial.ui.navegation.Screens

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun FormScreen(
    navController: NavController,
    innerPadding: PaddingValues
) {
    var city by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    val onClick: () -> Unit = {
        TODO()
    }

    Log.d(Tag, "FormScreen() -> composed / recomposed")

    Scaffold(
        topBar = {
            //TopAppBar(title = {Text(Screens.FormScreen.title)})
        },
        //containerColor = TODO()
    ) { innerPadding ->
        Column( modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth()
        ) {
            TextField(
                value = city,
                onValueChange = { newValue ->
                    city = newValue
                },
                label = {Text("Ciudad")},
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                  /*  focusedContainerColor = TODO(),
                    unfocusedContainerColor = TODO(),
                    unfocusedLabelColor = TODO() */
                )
            )

            TextField(
                value = date,
                onValueChange = { newValue ->
                    date = newValue
                },
                label = {Text("Fecha")},
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                   /* focusedContainerColor = TODO(),
                    unfocusedContainerColor = TODO(),
                    unfocusedLabelColor = TODO() */
                )
            )

            Button(modifier = Modifier
                .align(Alignment.End)
                .padding(all = 5.dp),
                onClick = onClick
            ) {
                Text("Buscar")
            }
        }

    }
}

private const val Tag = "FormScreen"