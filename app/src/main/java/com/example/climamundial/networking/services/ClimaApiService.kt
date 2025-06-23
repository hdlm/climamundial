package com.example.climamundial.networking.services

import com.example.climamundial.data.dtos.WeatherDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ClimaApiService {

    @GET("forecast")
    suspend fun fetchWeatherForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("appid") appid: String
    ): Response<WeatherDto>

}