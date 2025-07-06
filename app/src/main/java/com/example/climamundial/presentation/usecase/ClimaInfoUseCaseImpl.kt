package com.example.climamundial.presentation.usecase

import com.example.climamundial.data.dtos.WeatherDto
import com.example.climamundial.data.repositories.ClimaRepository
import com.example.climamundial.data.repositories.ClimaRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.Dispatcher
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.getScopeId

class ClimaInfoUseCaseImpl : ClimaInfoUseCase, KoinComponent {
    val repository: ClimaRepository
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