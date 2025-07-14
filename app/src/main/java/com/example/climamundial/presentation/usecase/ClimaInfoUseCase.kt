package com.example.climamundial.presentation.usecase

import com.example.climamundial.data.dtos.WeatherDto
import com.example.climamundial.data.repositories.ClimaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface ClimaInfoUseCase {
    /**
     * The method fetch the 'Weather' from the API RESTful
     * @return a Flow of WeatherDto
     */
    operator fun invoke(
        latitud: Double,
        longitud: Double,
        units: String,
    ): Flow<Result<WeatherDto>>

    operator fun invoke(
        latitud: Double,
        longitud: Double,
        units: String,
        onDone: (Result<WeatherDto>) -> Unit,
        scope: CoroutineScope
    )
}

