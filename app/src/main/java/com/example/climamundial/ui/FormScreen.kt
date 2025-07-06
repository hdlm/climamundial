package com.example.climamundial.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.climamundial.presentation.presenters.ClimateViewModel
import com.example.climamundial.ui.navegation.Screens
import org.koin.androidx.compose.koinViewModel

@Composable
fun FormScreen(
    navController: NavController,
    innerPadding: PaddingValues,
    viewModel: ClimateViewModel = koinViewModel()
) {
    var latText by remember { mutableStateOf<String>("") }
    var lonText by remember { mutableStateOf<String>("") }

    // LOG DE PRUEBA - Esto debería aparecer cuando se carga la pantalla
    Log.d(TAG, "========== FORMSCREEN INICIADA ==========")
    Log.i(TAG, "FormScreen composed/recomposed")
    Log.v(TAG, "Verbose log test")
    Log.w(TAG, "Warning log test")
    Log.e(TAG, "Error log test")

    // LOGS MÁS AGRESIVOS
    android.util.Log.e("PRUEBA_DEBUG", "========== ESTE LOG DEBERÍA APARECER ==========")
    android.util.Log.w("PRUEBA_DEBUG", "Warning log con tag diferente")
    android.util.Log.i("PRUEBA_DEBUG", "Info log con tag diferente")

    // También usando println
    println("========== PRINTLN TEST ==========")
    println("FormScreen cargada correctamente")

    val onClick: () -> Unit = {
        Log.d(TAG, "========== BOTÓN PRESIONADO ==========")
        Log.d(TAG, "Lat input: '$latText'")
        Log.d(TAG, "Lon input: '$lonText'")

        if (latText.isNotEmpty() && lonText.isNotEmpty()) {
            try {
                val lat = latText.toDouble()
                val lon = lonText.toDouble()

                Log.d(TAG, "Coordenadas convertidas: lat=$lat, lon=$lon")

                viewModel.setCoordinates(lat, lon)
                Log.d(TAG, "Coordenadas enviadas al ViewModel")

                val route = Screens.ClimateScreen.createRoute(latText, lonText)
                Log.d(TAG, "Ruta creada: $route")

                navController.navigate(route)
                Log.d(TAG, "Navegación ejecutada")

            } catch (e: NumberFormatException) {
                Log.e(TAG, "Error al convertir coordenadas: ${e.message}")
                Log.e(TAG, "LatText: '$latText', LonText: '$lonText'")
            }
        } else {
            Log.w(TAG, "Campos vacíos - Lat: '$latText', Lon: '$lonText'")
        }
    }

    // También log cuando cambian los campos
    Log.d(TAG, "Current values - Lat: '$latText', Lon: '$lonText'")

    Scaffold(
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                TextField(
                    value = latText,
                    onValueChange = { newValue ->
                        Log.d(TAG, "Lat changed from '$latText' to '$newValue'")
                        latText = newValue
                    },
                    enabled = true,
                    label = { Text("Latitud") },
                    placeholder = { Text("Ej. 33.44") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = lonText,
                    onValueChange = { newValue ->
                        Log.d(TAG, "Lon changed from '$lonText' to '$newValue'")
                        lonText = newValue
                    },
                    enabled = true,
                    label = { Text("Longitud") },
                    placeholder = { Text("Ej. -94.04") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(all = 5.dp),
                    onClick = {
                        Log.d(TAG, "Button clicked!")
                        onClick()
                    }
                ) {
                    Text("Continuar")
                }
            }
        }
    )
}

private const val TAG = "FormScreen"