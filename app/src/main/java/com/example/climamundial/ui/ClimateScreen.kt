package com.example.climamundial.ui

import android.util.Log
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.climamundial.presentation.presenters.ClimateScreenUiState
import com.example.climamundial.presentation.presenters.ClimateViewModel
import com.example.climamundial.ui.navegation.Screens
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.DividerProperties
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.Line
import ir.ehsannarmani.compose_charts.models.LineProperties
import ir.ehsannarmani.compose_charts.models.StrokeStyle
import ir.ehsannarmani.compose_charts.models.ZeroLineProperties
import org.koin.androidx.compose.koinViewModel



@Composable
fun  ClimateScreen(
    navController: NavController,
    innerPadding: PaddingValues,
    viewModel: ClimateViewModel = koinViewModel()
) {
    Log.i(TAG, "composed/recomposed")

    val climateScreenUiState by viewModel.uiState.collectAsStateWithLifecycle()
    when (val uiState = climateScreenUiState) {
        is ClimateScreenUiState.Loading -> {
            ClimateScreenLoading()
        }
        is ClimateScreenUiState.Error -> {
            ClimateScreenError(
                msg = uiState.errorMessage!!,
                onRetry = {}
            )
        }
        is ClimateScreenUiState.InputCoordinates -> {
            val setCoordinates: (Double, Double) -> Unit = { lat, lon ->
                Log.d(TAG, "setCoordinates() -> invoked, lat: $lat, lon: $lon")
                viewModel.setCoordinates(lat, lon)
            }
            InputCoordinatesData(
                navController = navController,
                setCoordinates = setCoordinates
            )
        }
        is ClimateScreenUiState.Ready -> {
            val onBackButtonClick: () -> Unit = {
                val value = navController.popBackStack()
                Log.d(TAG, "onBackButtonClick() -> clicked\n\treturned value: $value")
            }
            ShowWeather(
                uiState = uiState,
                onBackButtonClick = onBackButtonClick
            )
        }

    }

}





@Composable
fun ClimateScreenLoading(modifier: Modifier = Modifier){
    val areaSize = 94.dp

    Surface(modifier.fillMaxSize()) {
        Box {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(areaSize)
                    .align(Alignment.Center),
                strokeWidth = 8.dp,
                color = Color.Black
            )

        }
    }
}

@Composable
fun ClimateScreenError(msg: String, onRetry: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(
            text = "Ha ocurrido un error",
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text =  msg,
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = onRetry) {
            Text(text = "Volver")
        }
    }
}



@Composable
fun InputCoordinatesData(
    navController: NavController,
    setCoordinates: (Double, Double) -> Unit,
) {
    Log.i(TAG, "InputCoordinatesData() - composed/recomposed")

    var latText by remember { mutableStateOf<String>("") }
    var lonText by remember { mutableStateOf<String>("") }

    val onClick: () -> Unit = {
        Log.i(TAG, "========== BOTÓN PRESIONADO ==========")
        Log.d(TAG, "onClick() -> invoked")
        Log.d(TAG, "Lat input: '$latText', Lon input: '$lonText'")

        if (latText.isNotEmpty() && lonText.isNotEmpty()) {
            try {
                val lat = latText.toDouble()
                val lon = lonText.toDouble()

                setCoordinates.invoke(lat, lon)

            } catch (e: NumberFormatException) {
                Log.e(TAG, "Error al convertir coordenadas: ${e.message}")
                Log.e(TAG, "\tLatText: '$latText', LonText: '$lonText'")
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


@Composable
fun ShowWeather(
    uiState: ClimateScreenUiState.Ready,
    onBackButtonClick : () -> Unit = {},
) {
    Log.d(TAG, "ShowWeather() - composed / recomposed")

    val tempValues:List<Double> = uiState.weathers.temperatures

    Scaffold(
        topBar = {
            //TopAppBar(title = {Text(Screens.ClimateScreen.title)})
        },
        //containerColor = TODO()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
        ) {
            LineChart(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 25.dp, bottom = 50.dp, start = 25.dp, end = 50.dp),
                curvedEdges = false,
                dividerProperties = DividerProperties(
                    xAxisProperties = LineProperties(
                        color = SolidColor(Color.Black),
                        thickness = 1.dp
                    ),
                    yAxisProperties = LineProperties(
                        color = SolidColor(Color.Black),
                        thickness = 1.dp
                    )
                ),
                gridProperties = GridProperties(
                    enabled = false
                ),
                data = remember {
                    listOf(
                        Line(
                            label = "Temperatura",
                            values = tempValues,
                            color = SolidColor(Color.Red),
                            firstGradientFillColor = Color(0xFFf05539)
                                .copy(alpha = .5f),
                            secondGradientFillColor = Color(0xFF899fc3)
                                .copy(alpha = .5f),
                            strokeAnimationSpec = tween(2000,
                                easing = EaseInOutCubic),
                            gradientAnimationDelay = 1000,
                            drawStyle = DrawStyle.Stroke(
                                width =2.dp,
                                strokeStyle = StrokeStyle.Dashed(
                                    floatArrayOf(10f, 10f), phase = 15f
                                )
                            ),
                            dotProperties = DotProperties(
                                enabled = true,
                                radius = 3.5.dp,
                                color = SolidColor(Color.Red),
                                animationEnabled = true
                            )
                        )
                    )
                },
                animationMode = AnimationMode.Together(delayBuilder = {
                    it * 500L
                }),
                zeroLineProperties = ZeroLineProperties(
                    enabled = true,
                    color = SolidColor(Color.Red),
                    thickness = 1.5.dp
                ),
                minValue = -10.0,
                maxValue = 40.0
            )
        }
    }
}

private const val TAG = "ClimateScreen"