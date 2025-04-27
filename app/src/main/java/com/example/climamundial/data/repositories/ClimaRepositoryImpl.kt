package com.example.climamundial.data.repositories

import android.util.Log
import com.example.climamundial.data.dtos.CurrentWeatherDto
import com.example.climamundial.networking.RetrofitHelper
import com.example.climamundial.networking.RetrofitHelperImpl
import com.example.climamundial.networking.RetrofitHelperImpl.Companion.API_KEY
import com.example.climamundial.networking.services.ClimaApiService
import kotlin.coroutines.EmptyCoroutineContext.get

class ClimaRepositoryImpl : ClimaRepository {
    override suspend fun fetchCurrentWeather(
        latitud: Double,
        longitud: Double,
        appid: String
    ): CurrentWeatherDto? {
        val TAG = "ClimaRepositoryImpl "
        val myfun = "fetchCurrentWeather()"

        Log.d( TAG, "$myfun -> calling end-point with BASE_URL = ${RetrofitHelperImpl.BASE_URL}" )

        /*
        https://api.openweathermap.org/data/3.0/onecall?lat={lat}&lon={lon}&exclude={part}&appid={API key}
         */

        //val url_prueba = "https://api.openweathermap.org/data/3.0/onecall?lat=33.44&lon=-94.04&appid={API key}"
        val url = "${RetrofitHelperImpl.BASE_URL}onecall?lat=${latitud}&lon=${longitud}&appid=${API_KEY}"
        Log.d(TAG, "url: $url")

        var resp : CurrentWeatherDto? = null

        try {
            val retrofitHelper: RetrofitHelper = get()
            val response =
                (retrofitHelper.buildService(ClimaApiService::class.java).fetchClima(url))
            if (response.isSuccessful) {
                Log.d(TAG, "$myfun -> fetching conversion of weather successful")
                response.body()?.let {
                    resp = it
                }
            } else {
                throw Exception("The fetching of the Weather failed.")
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return resp
    }


}