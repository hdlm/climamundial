package com.example.climamundial.ui.navegation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector


sealed class Screens(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object ClimateScreen : Screens("ClimateScreen", "Clima", Icons.AutoMirrored.Filled.List)

}