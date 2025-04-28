package com.example.climamundial.data.repositories

import android.util.Log
import com.example.climamundial.data.dtos.Current
import com.example.climamundial.data.dtos.CurrentWeatherDto
import com.example.climamundial.networking.RetrofitHelper
import com.example.climamundial.networking.RetrofitHelperImpl
import com.example.climamundial.networking.RetrofitHelperImpl.Companion.API_KEY
import com.example.climamundial.networking.services.ClimaApiService
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.java.KoinJavaComponent.inject
import kotlin.getValue


class ClimaRepositoryImpl : ClimaRepository {
//    private val retrofitHelper by inject <RetrofitHelper>()
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

    //TODO crear un objeto con data dummy para devolverlo a la vista, descomentar el codigo de abajo
//        try {
//            val retrofitHelper: RetrofitHelper = get()
//            val response =
//                (retrofitHelper.buildService(ClimaApiService::class.java).fetchClima(url))
//            if (response.isSuccessful) {
//                Log.d(TAG, "$myfun -> fetching conversion of weather successful")
//                response.body()?.let {
////                    resp = it
//
//                    val mycurrent = Current(
//                        dt = TODO(),
//                        sunrise = TODO(),
//                        sunset = TODO(),
//                        temp = TODO(),
//                        feelsLike = TODO(),
//                        pressure = TODO(),
//                        humidity = TODO(),
//                        dewPoint = TODO(),
//                        uvi = TODO(),
//                        clouds = TODO(),
//                        visibility = TODO(),
//                        windSpeed = TODO(),
//                        windDeg = TODO(),
//                        windGust = TODO(),
//                        weather = TODO(),
//                        minutely = TODO(),
//                        hourly = TODO(),
//                        daily = TODO(),
//                        alerts = TODO()
//                    )
//                    val weatherDto: CurrentWeatherDto = CurrentWeatherDto(
//                        lat = TODO(),
//                        lon = TODO(),
//                        timeZone = TODO(),
//                        timeZoneOffset = TODO(),
//                        current = mycurrent
//                    )
//                    resp =  weatherDto
//                }
//            } else {
//                throw Exception("The fetching of the Weather failed.")
//            }
//        } catch (ex: Exception) {
//            ex.printStackTrace()
//        }

        return resp
    }


}