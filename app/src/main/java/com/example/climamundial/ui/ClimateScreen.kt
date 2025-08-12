package com.example.climamundial.ui

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.climamundial.R
import com.example.climamundial.presentation.presenters.ClimateScreenUiState
import com.example.climamundial.presentation.presenters.ClimateViewModel
import com.example.climamundial.presentation.presenters.GraphData
import com.example.climamundial.presentation.presenters.IconData
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
            val searchCity: (String) -> Unit = { cityName ->
                Log.d(TAG, "searchCity() -> invoked, city: $cityName")
                viewModel.searchCity(cityName)
            }
            InputCoordinatesData(
                navController = navController,
                searchCity = searchCity
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
    searchCity: (String) -> Unit,
) {
    Log.i(TAG, "InputCoordinatesData() - composed/recomposed")

    var city by remember { mutableStateOf<String>("") }

    val onClick: () -> Unit = {
        Log.d(TAG, "onClick() -> invoked")

        if (city.isNotEmpty()) {
            try {
                val cityName = city.trim().replaceFirstChar { it.uppercase() }
                searchCity(cityName)

            } catch (e: Exception) {
                Log.e(TAG, "Error al concultar el clima de la ciudad: ${e.message}")
            }
        } else {
            Log.w(TAG, "Campo vacío - Lat: '$city'")
        }
    }

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
                            value = city,
                            onValueChange = { newValue ->
                                Log.d(TAG, "Lat changed from '$city' to '$newValue'")
                                city = newValue
                            },
                            enabled = true,
                            label = { Text(
                                text ="Nombre de la Ciudad",
                                fontWeight = FontWeight.Bold
                                ) },
                            placeholder = { Text("Ej. Caracas") },
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
fun WeatherIcon(code: String, desc: String) {
    Log.d("WeatherIcon", "Código recibido: $code")
    val normalizedCode = code.trim().lowercase()

    val iconResId = when (normalizedCode) {
        // Cielo despejado
        "01d" -> R.drawable.dia
        "01n" -> R.drawable.noche

        // Pocas nubes
        "02d", "02n" -> R.drawable.pocas_nubes

        // Nubes dispersas
        "03d", "03n" -> R.drawable.nubes_dispersas

        // Nubes rotas / cubiertas
        "04d", "04n" -> R.drawable.nubes_dispersas

        // Lluvia ligera / chubascos
        "09d", "09n" -> R.drawable.lluvia

        // Lluvia
        "10d", "10n" -> R.drawable.lluvia_ligera

        // Tormenta eléctrica
        "11d", "11n" -> R.drawable.tormenta_electrica

        // Nieve
        "13d", "13n" -> R.drawable.nieve

        // Neblina
        "50d", "50n" -> R.drawable.neblina

        else -> {
            Log.w("WeatherIcon", "⚠️ Código no reconocido: $normalizedCode")
            R.drawable.error404
        }
    }

    Box(
        modifier = Modifier.fillMaxWidth().padding(top = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = desc,
            modifier = Modifier.size(150.dp)
        )
    }
}

fun weatherDescTranslate(desc: String): String {
    return when (desc) {
        // Cielo despejado
        "01d" -> "Día Despejado"
        "01n" -> "Noche Despejada"

        // Pocas nubes
        "02d", "02n" -> "Pocas Nubes"

        // Nubes dispersas
        "03d", "03n" -> "Nubes Dispersas"

        // Nubes rotas / cubiertas
        "04d", "04n" -> "Nubes Rotas"

        // Lluvia ligera / chubascos
        "09d", "09n" -> "Lluvia Ligera"

        // Lluvia
        "10d", "10n" -> "Lluvia"

        // Tormenta eléctrica
        "11d", "11n" -> "Tormenta Eléctrica"

        // Nieve
        "13d", "13n" -> "Nieve"

        // Neblina
        "50d", "50n" -> "Neblina"

        else -> "Condición desconocida"
    }
}

@Composable
fun ShowWeather(
    uiState: ClimateScreenUiState.Ready,
) {
    Log.d(TAG, "ShowWeather() - composed / recomposed")

    val iconCode = uiState.iconData.iconCode
    val iconDescription = uiState.iconData.iconDesc

    val weatherDesc = weatherDescTranslate(iconCode)

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

    //TODO() Separar la gráfica por día
//    val lines = uiState.weathers.dailyTemperature.entries.mapIndexed { index, entry ->
//        val (day, temps) = entry
//
//        val parsedDate = inputFormat.parse(day)
//        val dateLabel = outputFormat.format(parsedDate)
//
//        Line(
//                                label = dateLabel,
//                                values = temps,
//                                color = SolidColor(colorPalette[index % colorPalette.size]),
//                                strokeAnimationSpec = tween(2000,
//                                    easing = EaseInOutCubic),
//                                drawStyle = DrawStyle.Stroke(
//                                    width =2.dp,
//                                ),
//                                dotProperties = DotProperties(
//                                    enabled = true,
//                                    radius = 3.5.dp,
//                                    color = SolidColor(Color.Red),
//                                    animationEnabled = true,
//                                )
//                            )
//    }

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
                    text = "Clima de ${uiState.cityName} (Esta Semana)",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .align(Alignment.CenterHorizontally)
                )

                WeatherIcon(iconCode, iconDescription)

                Text(
                    text = weatherDesc,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .align(Alignment.CenterHorizontally)
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
            cityName = "Los Teques",
            iconData = IconData(
                iconCode = "01d",
                iconDesc = ""
            )
        )
        ShowWeather(
            mockReadyState
        )
    }
}


