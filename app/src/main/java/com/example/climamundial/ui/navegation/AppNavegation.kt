package com.example.climamundial.ui.navegation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.climamundial.ui.ClimateScreen
import com.example.climamundial.ui.InputCoordinatesData

@Composable
fun AppNavigation(
    navController: NavHostController,
    startDest: Screens,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Screens.ClimateScreen.route
    ) {
        composable(
            route = Screens.ClimateScreen.route,
        ) { backStackEntry ->
            ClimateScreen(
                navController = navController,
                innerPadding = innerPadding
            )
        }
    }
}