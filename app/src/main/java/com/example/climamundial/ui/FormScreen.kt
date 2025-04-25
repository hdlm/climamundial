package com.example.climamundial.ui

import android.util.Log
import androidx.annotation.OptIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.climamundial.ui.navegation.AppNavigation
import com.example.climamundial.ui.navegation.Screens

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun FormScreen(
    navController: NavController,
    innerPadding: PaddingValues
) {
    var selectedOption by remember { mutableStateOf<String?>(null) }
    val onClick: () -> Unit = {
        Log.d(TAG, "onChangeScreenClick() -> invoked")
        val changeScreen = Screens.ClimateScreen.route
        navController.navigate(changeScreen)
    }
    Log.d(TAG, "FormScreen() -> composed / recomposed")

    Scaffold(
//        topBar = {
//            TopAppBar(title = {Text(Screens.FormScreen.title)})
//        },
        //containerColor =
        content = { innerPadding ->
            Column( modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                

                    Button(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(all = 5.dp),
                        onClick = onClick
                    ) {
                        Text("Continuar")
                    }
                }


        }
    )
}


private const val TAG = "FormScreen"