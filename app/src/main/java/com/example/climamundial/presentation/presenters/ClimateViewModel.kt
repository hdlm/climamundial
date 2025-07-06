package com.example.climamundial.presentation.presenters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climamundial.presentation.usecase.ClimaInfoUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class GraphData (
    val temperatures: List<Double>
)

@OptIn(ExperimentalCoroutinesApi::class)
class ClimateViewModel : ViewModel(), KoinComponent {

    private val climateInfoUseCase: ClimaInfoUseCase by inject()

    private val _coordinates = MutableStateFlow<Pair<Double, Double>?>(null)

    private val _uiState = MutableStateFlow<ClimateScreenUiState>(ClimateScreenUiState.Loading)
    val uiState: StateFlow<ClimateScreenUiState>
        get() = _uiState

    init {
        viewModelScope.launch {
            _coordinates
                .filterNotNull()
                .flatMapLatest { (lat, lon) ->
                    climateInfoUseCase(lat, lon, "metric")
                        .onStart { _uiState.value = ClimateScreenUiState.Loading }
                }
                .catch { throwable ->
                    _uiState.value = ClimateScreenUiState.Error(throwable.localizedMessage)
                }
                .collect { result ->
                    result.fold(
                        onSuccess = { weatherDto ->
                            val temperatures = weatherDto.list.map { it.main.temp }
                            val graphData = GraphData(temperatures = temperatures)
                            _uiState.value = ClimateScreenUiState.Ready(graphData)
                        },
                        onFailure = { error ->
                            _uiState.value = ClimateScreenUiState.Error(error.message)
                        }
                    )
                }
        }
    }
    // Función para actualizar las coordenadas, lo que dispara la consulta
    fun setCoordinates(lat: Double, lon: Double) {
        _coordinates.value = Pair(lat, lon)
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

    data class Ready(
    val weathers: GraphData
        ): ClimateScreenUiState
}