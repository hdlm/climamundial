package com.example.climamundial.ui.navegation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object ClimateScreen :
        Screens("climate/{lat}/{lon}", "Clima", Icons.AutoMirrored.Filled.List) {
        // Helper para crear la ruta con valores
        fun createRoute(lat: String, lon: String) = "climate/$lat/$lon"
    }
    data object FormScreen: Screens(route = "FormScreen", title = "Formulario", icon = Icons.AutoMirrored.Filled.List)

}