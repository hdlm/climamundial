package com.example.climamundial.data.repositories

import android.util.Log
import com.example.climamundial.data.dtos.WeatherDto
import com.example.climamundial.networking.RetrofitHelper
import com.example.climamundial.networking.RetrofitHelperImpl
import com.example.climamundial.networking.RetrofitHelperImpl.Companion.API_KEY
import com.example.climamundial.networking.services.ClimaApiService
import org.koin.core.component.KoinComponent
import org.koin.core.component.get


/** Prueba _integral_ para validar la carga de los datos a traves del web service */
class ClimaRepositoryImpl : ClimaRepository, KoinComponent {
    override suspend fun fetchCurrentWeather(
        latitud: Double,
        longitud: Double,
        units: String,
    ): Result<WeatherDto> {
        val myfun = "fetchCurrentWeather()"
        Log.i(TAG, "$myfun -> calling end-point with BASE_URL = ${RetrofitHelperImpl.BASE_URL}")

        return try {
            val retrofitHelper: RetrofitHelper = get()
            val response =
                (retrofitHelper.buildService(ClimaApiService::class.java)
                    .fetchWeatherForecast(
                        lat = latitud,
                        lon = longitud,
                        units = units,
                        appid = API_KEY
                    ))
            if (response.isSuccessful) {
                val weatherResponse = response.body()
                if (weatherResponse != null) {
                    Log.d(TAG, "$myfun -> fetching conversion of weather successful")
                    Result.success(weatherResponse)
                } else {
                    val errorMessage = "API call successful but response body it is null"
                    Log.e(TAG, "$myfun -> $errorMessage")
                    Result.failure(Exception(errorMessage))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = "Fetching weather failed with code ${response.code()}: $errorBody"
                Log.e(TAG, "$myfun -> $errorMessage")
                Result.failure(Exception(errorMessage))
            }
        } catch (ex: Exception) {
            Log.e(TAG, "$myfun -> Exception during weather fetch: ${ex.message}")
            Result.failure(ex)
        }

    }

    companion object {
        const val TAG = "zod.ClimaRepositoryImpl"
    }
}

