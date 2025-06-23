package com.example.climamundial.data.repositories

import com.example.climamundial.data.dtos.WeatherDto

interface ClimaRepository {

    // Current and forecasts weather data
    suspend fun fetchCurrentWeather(latitud: Double, longitud: Double, units: String): Result<WeatherDto>
}