package com.example.climamundial.presentation.usecase

import com.example.climamundial.data.dtos.WeatherDto
import com.example.climamundial.data.repositories.ClimaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class ClimaInfoUseCaseImpl : ClimaInfoUseCase, KoinComponent {
    override val repository: ClimaRepository
        get() = get()

    override fun invoke(
        latitud: Double,
        longitud: Double,
        units: String
    ): Flow<Result<WeatherDto>> = flow {
        val result = repository.fetchCurrentWeather(
            latitud = latitud,
            longitud = longitud,
            units = units,
        )
        emit(result)
    }

}