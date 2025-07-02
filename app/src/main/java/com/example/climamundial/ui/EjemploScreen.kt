package com.example.climamundial.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.climamundial.R
import com.example.climamundial.ui.theme.ClimaMundialTheme
import kotlinx.coroutines.launch

@Composable
fun EjemploScreen() {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    val horizontalMargin = dimensionResource(R.dimen.horizontal_margin)
    val verticalMargin = dimensionResource(R.dimen.horizontal_margin)
    val lineSpacing = dimensionResource(R.dimen.line_spacing_2x)
    val nombre: String = "Carlos"
    var edad by remember { mutableStateOf("") }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
            .verticalScroll(scrollState)
        ) {
            Text("Hola mi pana: ${nombre}")
            Text("El dia de hoy veremos")
            Text("jetpack compose")
            TextField(
                value = edad,
                onValueChange = { newValue ->
                    val filteredValue = newValue.filter { it.isDigit() }
                    edad = filteredValue
                },
                enabled = true,
                label = { Text("Introduzca la Edad") }, // Usa un Composable Text para el label
                placeholder = { Text("Edad") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), // Muestra teclado numérico
                modifier = Modifier.fillMaxWidth() // Haz que el TextField ocupe todo el ancho disponible
            )
            Button(
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Tu edad es: $edad",
                            withDismissAction = true
                        )
                    }
                }
            ) {
                Text("Guardar")
            }
            Spacer(modifier = Modifier.height(lineSpacing))
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .size(
                        height = 1024.dp,
                        width = 100.dp
                    )
                    .heightIn(max = 512.dp)
            ) {
                Row (modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = horizontalMargin)
                ) {
                    Text( modifier = Modifier.weight(0.4f)
                        .padding(vertical = verticalMargin),
                        text = "Que bueno esta el helado, vale!"
                    )
                    Image(
                        painter = painterResource(R.drawable.comiendo_helado),
                        contentDescription = stringResource(R.string.image_content_description),
                        modifier = Modifier.weight(0.6f)
                            .size(width = 512.dp, height = 1024.dp)
                            .padding(vertical = verticalMargin)
                            .clickable {

                            }
                    )
                }
            }


        }
    }
}

@Composable
@Preview(showBackground = true)
fun EjemploScreenPreview() {
    ClimaMundialTheme {
        EjemploScreen()
    }
}

