package com.example.climamundial.data.repositories

import com.example.climamundial.data.dtos.GeoDto

interface GeoRepository {
    suspend fun searchCityByName(city: String, limit: Int): Result<List<GeoDto>>
}