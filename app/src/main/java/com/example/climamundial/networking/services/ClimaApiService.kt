package com.example.climamundial.networking.services

import com.example.climamundial.data.dtos.WeatherDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ClimaApiService {

    @GET("data/2.5/forecast")
    suspend fun fetchWetherForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("appid") appid: String
    ): Response<WeatherDto>

}