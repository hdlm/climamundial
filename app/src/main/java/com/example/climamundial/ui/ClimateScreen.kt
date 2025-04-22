package com.example.climamundial.ui

import android.util.Log
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun ClimateScreen(
    navController: NavController,
    innerPadding: PaddingValues
) {
    Log.d(Tag, "CliamteScreen() -> composed / recomposed")
}

private const val Tag = "ClimateScreen"