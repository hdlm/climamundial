package com.example.climamundial.data.repositories

import com.example.climamundial.data.dtos.CurrentWeatherDto

interface ClimaRepository {

    // Current and forecasts weather data
    suspend fun fetchCurrentWeather(latitud: Double, longitud: Double, appid: String): CurrentWeatherDto?
}