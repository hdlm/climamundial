package com.example.climamundial.networking.services

import com.example.climamundial.data.dtos.CurrentWeatherDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ClimaApiService {

    @GET
    suspend fun fetchClima(@Url url:String) : Response<CurrentWeatherDto>

}