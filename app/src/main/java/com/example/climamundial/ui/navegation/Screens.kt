package com.example.climamundial.ui.navegation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object ClimateScreen: Screens(route = "ClimateScreen", title = "Clima", icon = Icons.AutoMirrored.Filled.List)
    object FormScreen: Screens(route = "FormScreen", title = "Formulario", icon = Icons.AutoMirrored.Filled.List)

}