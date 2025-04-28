package com.example.climamundial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.climamundial.commons.CommonsValues
import com.example.climamundial.di.Modules.appModule
import com.example.climamundial.ui.FormScreen
import com.example.climamundial.ui.navegation.AppNavigation
import com.example.climamundial.ui.navegation.Screens
import com.example.climamundial.ui.theme.ClimaMundialTheme
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.error.ApplicationAlreadyStartedException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        try {
            startKoin {
                androidContext(this@MainActivity)
                androidLogger()
                modules(appModule)
            }
        } catch (ex: ApplicationAlreadyStartedException) {
            // ignore
        }

        CommonsValues.context = applicationContext

        setContent {
            ClimaMundialTheme {
                MainScreen()
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ClimaMundialTheme {
        Greeting("Android")
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val navigationItems = listOf(
        Screens.FormScreen,
        Screens.ClimateScreen
    )

    Scaffold(
        content = { innerPadding ->
            AppNavigation(
                navController = navController,
                startDest = Screens.FormScreen,
                innerPadding = innerPadding
            )
        }
    )
}