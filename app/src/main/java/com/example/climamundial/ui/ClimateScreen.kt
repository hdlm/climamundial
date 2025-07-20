package com.example.climamundial.ui

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.climamundial.presentation.presenters.ClimateScreenUiState
import com.example.climamundial.presentation.presenters.ClimateViewModel
import com.example.climamundial.presentation.presenters.GraphData
import com.example.climamundial.ui.theme.ClimaMundialTheme
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.DividerProperties
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.Line
import ir.ehsannarmani.compose_charts.models.LineProperties
import ir.ehsannarmani.compose_charts.models.StrokeStyle
import ir.ehsannarmani.compose_charts.models.ZeroLineProperties
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Locale


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
            BackHandler {
                viewModel.setUiState(ClimateScreenUiState.InputCoordinates)
            }

            ShowWeather(
                uiState = uiState
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
        topBar = {},
        contentColor = Color(0xFF80D8FF),
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ){
                Card(
                    modifier = Modifier
                        .padding(all = 10.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF80D8FF))
                ) {
                    Text(
                        text = "Clima Mundial",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Column(
                        modifier = Modifier
                            .padding(all = 16.dp)
                            .fillMaxWidth()
                            .background(Color(0xFF80D8FF)),
                        verticalArrangement = Arrangement.Center,

                    ) {
                        OutlinedTextField(
                            value = latText,
                            onValueChange = { newValue ->
                                Log.d(TAG, "Lat changed from '$latText' to '$newValue'")
                                latText = newValue
                            },
                            enabled = true,
                            label = { Text(
                                text ="Latitud",
                                fontWeight = FontWeight.Bold
                                ) },
                            placeholder = { Text("Ej. 10.50") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedLabelColor = Color.DarkGray,
                                focusedBorderColor = Color(0xFF4FA67B),
                                unfocusedBorderColor = Color(0xFF4FA67B),
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                cursorColor = Color(0xFF4FA67B),
                                focusedTextColor = Color.Black
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp, top = 2.dp)
                        )

                        OutlinedTextField(
                            value = lonText,
                            onValueChange = { newValue ->
                                Log.d(TAG, "Lon changed from '$lonText' to '$newValue'")
                                lonText = newValue
                            },
                            enabled = true,
                            label = { Text(
                                text = "Longitud",
                                fontWeight = FontWeight.Bold,
                                ) },
                            placeholder = { Text("Ej. -66.93") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedLabelColor = Color.DarkGray,
                                focusedBorderColor = Color(0xFF4FA67B),
                                unfocusedBorderColor = Color(0xFF4FA67B),
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                cursorColor = Color(0xFF4FA67B),
                                focusedTextColor = Color.Black
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp, top = 2.dp)
                        )

                        Button(
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(all = 5.dp),
                            colors = ButtonColors(
                                containerColor = Color(0xFF4FA67B),
                                contentColor = Color.White,
                                disabledContentColor = Color.DarkGray,
                                disabledContainerColor = Color.LightGray
                            ),
                            onClick = {
                                Log.d(TAG, "Button clicked!")
                                onClick()
                            }
                        ) {
                            Text("Continuar")
                        }
                    }
                }
            }
        }
    )
}


@Composable
fun ShowWeather(
    uiState: ClimateScreenUiState.Ready,
) {
    Log.d(TAG, "ShowWeather() - composed / recomposed")

    val tempValues:List<Double> = uiState.weathers.temperatures
    val colorPalette = listOf(
        Color(0xFFFF7043), // naranja
        Color(0xFF42A5F5), // azul
        Color(0xFF66BB6A), // verde
        Color(0xFFAB47BC), // violeta
        Color(0xFFFFCA28)  // amarillo
    )

    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("d MMM", Locale("es", "ES"))

    val lines = uiState.weathers.dailyTemperature.entries.mapIndexed { index, entry ->
        val (day, temps) = entry

        val parsedDate = inputFormat.parse(day)
        val dateLabel = outputFormat.format(parsedDate)

        Line(
                                label = dateLabel,
                                values = temps,
                                color = SolidColor(colorPalette[index % colorPalette.size]),
                                strokeAnimationSpec = tween(2000,
                                    easing = EaseInOutCubic),
                                drawStyle = DrawStyle.Stroke(
                                    width =2.dp,
                                ),
                                dotProperties = DotProperties(
                                    enabled = true,
                                    radius = 3.5.dp,
                                    color = SolidColor(Color.Red),
                                    animationEnabled = true,
                                )
                            )
    }

    Scaffold(
        topBar = {},
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp, top = 25.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Clima de ${uiState.cityName}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .align(Alignment.CenterHorizontally)
                )

                //TODO() Futuro espacio para un icono
                Spacer(
                    modifier = Modifier.padding(all = 150.dp)
                )

                LineChart(
                    labelProperties = LabelProperties(
                        enabled = true,
                        textStyle = MaterialTheme.typography.bodyMedium,
                        //labels = listOf("C°"),
                    ),
                    labelHelperProperties = LabelHelperProperties(
                        enabled = true,
                        textStyle = MaterialTheme.typography.labelMedium
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 25.dp, bottom = 50.dp, start = 25.dp, end = 50.dp),
                    curvedEdges = true,
                    dividerProperties = DividerProperties(
                        xAxisProperties = LineProperties(
                            color = SolidColor(Color.Black),
                            thickness = 1.dp
                        ),
                        yAxisProperties = LineProperties(
                            color = SolidColor(Color.Black),
                            thickness = 1.dp,
                        )
                    ),
                    gridProperties = GridProperties(
                        enabled = true,
                        yAxisProperties = GridProperties.AxisProperties(
                            color = SolidColor(Color(0xFF2C2C2C)),
                            style = StrokeStyle.Dashed(floatArrayOf(4f, 4f))
                        )
                    ),
                    data =
                        remember {
                        listOf(
                            Line(
                                label = "Temperatura en C°",
                                values = tempValues,
                                color = SolidColor(Color(0xFF26A69A)),
                                firstGradientFillColor = Color(0xFF66BB6A)
                                    .copy(alpha = .5f),
                                secondGradientFillColor = Color(0x00FFFFFF)
                                    .copy(alpha = .5f),
                                strokeAnimationSpec = tween(2000,
                                    easing = EaseInOutCubic),
                                gradientAnimationDelay = 1000,
                                drawStyle = DrawStyle.Stroke(
                                    width =2.dp,
                                    //strokeStyle = StrokeStyle.Dashed(
                                    //    floatArrayOf(10f, 10f), phase = 15f
                                    // )
                                ),
                                dotProperties = DotProperties(
                                    enabled = true,
                                    radius = 3.5.dp,
                                    color = SolidColor(Color(0xFF26A69A)),
                                    animationEnabled = true,
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
}

private const val TAG = "ClimateScreen"

@Composable
@Preview(showBackground = true)
fun ShowWeatherPreview() {
    ClimaMundialTheme {
        val mockReadyState = ClimateScreenUiState.Ready(
            weathers = GraphData(
                temperatures = listOf(23.5, 25.0, 22.0, 27.3, 24.8)
            ),
            cityName = "Los Teques"
        )
        ShowWeather(
            mockReadyState
        )
    }
}


