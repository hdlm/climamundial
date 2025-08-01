package com.example.climamundial.data.repositories

import android.util.Log
import com.example.climamundial.data.dtos.GeoDto
import com.example.climamundial.networking.RetrofitHelper
import com.example.climamundial.networking.RetrofitHelperImpl
import com.example.climamundial.networking.RetrofitHelperImpl.Companion.API_KEY
import com.example.climamundial.networking.services.GeocodingApiService
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class GeoRepositoryImpl(
    private val retrofitHelper: RetrofitHelper
): GeoRepository, KoinComponent {
    override suspend fun searchCityByName(
        city: String,
        limit: Int
    ): Result<List<GeoDto>> {
        val myfun = "searchCityByName()"
        Log.i(TAG, "$myfun -> calling end-point with BASE_URL = ${RetrofitHelperImpl.BASE_URL}")

        return try {
            val response =
                (retrofitHelper.buildService(GeocodingApiService::class.java).
                    searchCity(
                    city = city,
                    limit = 1,
                    apiKey = API_KEY
                ))
            if (response.isSuccessful) {
                val geoResponse = response.body()
                if (geoResponse != null) {
                    Log.d(TAG, "$myfun -> lat and lon data collect successful")
                    Result.success(geoResponse)
                } else {
                    val errorMessage = "API call successful but response body it is null"
                    Log.e(TAG, "$myfun -> $errorMessage")
                    Result.failure(Exception(errorMessage))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = "Geocoding API call failed with code ${response.code()}: $errorBody"
                Log.e(TAG, "$myfun -> $errorMessage")
                Result.failure(Exception(errorMessage))
            }
        } catch (ex: Exception) {
            Log.e(TAG, "$myfun -> Exception during weather fetch: ${ex.message}")
            Result.failure(ex)
        }
    }
    companion object {
        const val TAG = "GeoRpositoryImpl"
    }
}

