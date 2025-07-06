package com.example.climamundial.ui.navegation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.climamundial.ui.ClimateScreen
import com.example.climamundial.ui.FormScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    startDest: Screens,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Screens.FormScreen.route
    ) {
        //Formulario
        composable(Screens.FormScreen.route) {
            FormScreen(
                navController = navController,
                innerPadding = innerPadding,
            )
        }

        //Clima
        composable(
            route = Screens.ClimateScreen.route,
            arguments = listOf(
                navArgument("lat") { type = NavType.StringType },
                navArgument("lon") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val lat = backStackEntry.arguments?.getString("lat") ?: ""
            val lon = backStackEntry.arguments?.getString("lon") ?: ""

            ClimateScreen(
                navController = navController,
                innerPadding = innerPadding
            )
        }
}
    }