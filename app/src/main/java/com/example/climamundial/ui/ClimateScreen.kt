package com.example.climamundial.ui

import android.util.Log
import androidx.annotation.OptIn
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClimateScreen(
    navController: NavController,
    innerPadding: PaddingValues
) {

    var tempValues: List<Double> = listOf(19.5, 21.7, 24.2, 22.0, 22.9, 21.5, 19.0, 18.6)
    Log.d(TAG, "ClimateScreen() -> composed / recomposed")

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