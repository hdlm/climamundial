package com.example.climamundial.networking.services

import com.example.climamundial.data.dtos.GeoDto
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingApiService {
    @GET("geo/1.0/direct")
    suspend fun searchCity(
        @Query("q") city: String,
        @Query("limit") limit: Int = 1,
        @Query("appid") apiKey: String
    ): Response<List<GeoDto>>
}