package com.example.climamundial.presentation.presenters

import android.health.connect.datatypes.units.Temperature
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climamundial.commons.util.combine
import com.example.climamundial.data.dtos.WeatherDto
import com.example.climamundial.presentation.usecase.ClimaInfoUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.time.Instant
import java.time.ZoneOffset

data class GraphData (
    val temperatures: List<Double>,
    val dailyTemperature: Map<String, List<Double>> = mapOf()
)

@OptIn(ExperimentalCoroutinesApi::class)
class ClimateViewModel : ViewModel(), KoinComponent {

    private val climateInfoUseCase: ClimaInfoUseCase by inject()

    private val _weather = MutableStateFlow<Result<WeatherDto>?>(null)
    private var _coordinates = MutableStateFlow<Pair<Double,Double>>(Pair(1000.0,1000.0))
    val coordinates: Pair<Double,Double>
        get() = _coordinates.value


    private val _uiState = MutableStateFlow<ClimateScreenUiState>(ClimateScreenUiState.Loading)
    val uiState: StateFlow<ClimateScreenUiState>
        get() = _uiState

    private val refreshing = MutableStateFlow<Boolean>(false)

    init {
        viewModelScope.launch {
            val scope = this
            val onDone: (Result<WeatherDto>) -> Unit = { result ->
                Log.i(TAG, "onDone() -> invoked")
                _weather.value = result
            }

            combine(
                _weather,
                _coordinates,
                refreshing
            ) { weather, coordinates, refreshing ->

                if (refreshing) {
                    return@combine ClimateScreenUiState.Loading
                }

                if (coordinates.first == 1000.0 && coordinates.second == 1000.0) {
                    ClimateScreenUiState.InputCoordinates

                } else {
                    weather?.let { weatherResult ->
                        if (weatherResult.isFailure) {
                            ClimateScreenUiState.Error(weatherResult.exceptionOrNull()?.message)
                        } else {
                            val result = weatherResult.getOrNull()
                            if (result != null) {
                                val city = result.city.name
                                val temperatures: List<Double> = result.list.map { it.main.temp }
                                val daily = result.list
                                    .groupBy { item ->
                                        val instant = Instant.ofEpochSecond(item.dt)                 // convierte a fecha
                                        val localDate = instant.atZone(ZoneOffset.UTC).toLocalDate() // ajusta zona si necesario
                                        localDate.toString()                                         // "2025-07-21"
                                    }
                                    .mapValues { it.value.map { w -> w.main.temp } }
                                val graphData: GraphData = GraphData(
                                    temperatures = temperatures,
                                    dailyTemperature = daily
                                )
                                ClimateScreenUiState.Ready(
                                    weathers = graphData,
                                    cityName = city
                                )
                            } else {
                                ClimateScreenUiState.Error("No data available")
                            }
                        }
                    } ?: run {
                        climateInfoUseCase.invoke(coordinates.first, coordinates.second, "metric", onDone, scope)
                        ClimateScreenUiState.Loading
                    }

                }

            }.catch { throwable ->
                _uiState.value = ClimateScreenUiState.Error(throwable.localizedMessage)
            }.collect { uiState ->
                _uiState.value = uiState
            }
        }

        refreshing.value = false
    }


   private fun refresh(force: Boolean = false) {
       viewModelScope.launch {
           refreshing.value = true
           delay(2000)
           refreshing.value = false
       }
   }

    // Función para actualizar las coordenadas, lo que dispara la consulta
    fun setCoordinates(lat: Double, lon: Double) {
        _coordinates.value = Pair(lat, lon)
    }

    fun setUiState(newState: ClimateScreenUiState) {
        _uiState.value = newState
    }

    companion object {
        const val TAG = "ClimateViewModel"
    }
}


sealed interface ClimateScreenUiState {
    data object Loading: ClimateScreenUiState

    data class Error(
        val errorMessage: String? = null
        ): ClimateScreenUiState

    data object InputCoordinates: ClimateScreenUiState

    data class Ready(
        val weathers: GraphData,
        val cityName: String
    ): ClimateScreenUiState
}