package com.example.climamundial.presentation.presenters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climamundial.data.dtos.CurrentWeatherDto
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent


@OptIn(ExperimentalCoroutinesApi::class)
class ClimateViewModel : ViewModel(), KoinComponent {
    private val _weather = MutableStateFlow<List<CurrentWeatherDto>>(emptyList())

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
        val weathers: List<CurrentWeatherDto> = emptyList()
    ): ClimateScreenUiState
}