package com.example.climamundial.ui.navegation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.climamundial.ui.ClimateScreen
import com.example.climamundial.ui.FormScreen

@Composable

fun AppNavigation(
    navController: NavHostController,
    startDest: Screens,
    innerPadding: PaddingValues
) {
    NavHost(navController = navController, startDestination = startDest.route){
        //Pantalla del clima
        composable(Screens.ClimateScreen.route) {
            ClimateScreen(
                navController = navController,
                innerPadding = innerPadding
            )
        }
        //Pantalla de búsqueda (formulario)
        composable(Screens.FormScreen.route) {
            FormScreen(
                navController = navController,
                innerPadding = innerPadding
            )
        }
    }
}



