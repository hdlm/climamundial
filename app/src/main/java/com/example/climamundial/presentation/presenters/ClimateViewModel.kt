package com.example.climamundial.presentation.presenters

import androidx.lifecycle.ViewModel
import com.example.climamundial.data.dtos.WeatherDto
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent


@OptIn(ExperimentalCoroutinesApi::class)
class ClimateViewModel : ViewModel(), KoinComponent {
    private val _weather = MutableStateFlow<List<WeatherDto>>(emptyList())

    private val refreshing = MutableStateFlow(false)

    private val _uiState = MutableStateFlow<ClimateScreenUiState>(ClimateScreenUiState.Loading)
    val uiState: StateFlow<ClimateScreenUiState>
        get() = _uiState

    init {
//        viewModelScope.launch {
//            com.example.climamundial.commons.combine(
//
//            )
//        }
    }
}

sealed interface ClimateScreenUiState {
    data object Loading: ClimateScreenUiState

    data class Error(
        val errorMessage: String? = null
    ): ClimateScreenUiState

    data class Ready(
        val weathers: List<WeatherDto> = emptyList()
    ): ClimateScreenUiState
}