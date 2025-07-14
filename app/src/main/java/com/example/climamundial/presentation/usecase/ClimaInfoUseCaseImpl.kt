package com.example.climamundial.presentation.usecase

import com.example.climamundial.data.dtos.WeatherDto
import com.example.climamundial.data.repositories.ClimaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class ClimaInfoUseCaseImpl : ClimaInfoUseCase, KoinComponent {
    val repository: ClimaRepository
        get() = get()

    //TODO esta funcion esta dando un error Debido al Flow y la consulta a una coroutine asyncrona
    override fun invoke(
        latitud: Double,
        longitud: Double,
        units: String,
//        scope: CoroutineScope
    ): Flow<Result<WeatherDto>> = channelFlow {
//        scope.launch {
//            val deferredResult =  fetchCurrentWeatherAsync(latitud, longitud, units, scope)
//            val result = deferredResult.await()
//            send(result)
//        }
    }

    override fun invoke(
        latitud: Double,
        longitud: Double,
        units: String,
        onDone: (Result<WeatherDto>) -> Unit,
        scope: CoroutineScope
    ): Unit {
        scope.launch(Dispatchers.IO) {
            val deferredResult =  fetchCurrentWeatherAsync(latitud, longitud, units, scope)
            val result = deferredResult.await()
            onDone.invoke(result)
        }
    }

    private suspend fun fetchCurrentWeatherAsync(
        latitud: Double,
        longitud: Double,
        units: String,
        scope: CoroutineScope
    ) : Deferred<Result<WeatherDto>> = scope.async {
        repository.fetchCurrentWeather(
            latitud = latitud,
            longitud = longitud,
            units = units,
        )
    }

}