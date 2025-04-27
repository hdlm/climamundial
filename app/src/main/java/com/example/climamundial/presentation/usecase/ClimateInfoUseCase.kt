package com.example.climamundial.presentation.usecase

import com.example.climamundial.data.dtos.CurrentWeatherDto
import com.example.climamundial.data.repositories.ClimaRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class ClimateInfoUseCase: KoinComponent {
    private val lat: Double
        get() = TODO()

    private val lon: Double
        get() = TODO()

    private val climaRepository: ClimaRepository
        get() = get()

    operator fun invoke(): Flow<List<CurrentWeatherDto>> =
        climaRepository.fetchCurrentWeather(latitud = lat, longitud = lon, appid = )
}

