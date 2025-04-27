package com.example.climamundial.presentation.presenters

import androidx.compose.ui.graphics.Path.Companion.combine
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climamundial.data.dtos.CurrentWeatherDto
import com.example.climamundial.ui.ClimateScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch


@OptIn(ExperimentalCoroutinesApi::class)
class ClimateScreenViewModel : ViewModel(), KoinComponent {
    private val _weather = MutableStateFlow<List<CurrentWeatherDto>>(emptyList())

    private val refreshing = MutableStateFlow(false)

    private val _uiState = MutableStateFlow<ClimateScreenUiState>(ClimateScreenUiState.Loading)
    val uiState: StateFlow<ClimateScreenUiState>
        get() = _uiState

    init {
        viewModelScope.launch {
            com.example.climamundial.commons.combine(

            )
        }
    }
}

sealed interface ClimateScreenUiState {
    data object Loading: ClimateScreenUiState

    data class Error(
        val errorMessage: String? = null
    ): ClimateScreenUiState

    data class Ready(
        val weathers: List<CurrentWeatherDto> = emptyList()
    ): ClimateScreenUiState
}