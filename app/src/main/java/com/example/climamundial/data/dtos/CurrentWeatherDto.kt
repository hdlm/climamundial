package com.example.climamundial.data.dtos

import com.google.gson.annotations.SerializedName

data class CurrentWeatherDto(
    val lat: Double,
    val lon: Double,
    @SerializedName("timezone") val timeZone: String,
    @SerializedName("timezone_offset") val timeZoneOffset: Int,
    val current: Current,
)

data class  Current(
    val dt: Int,
    val sunrise: Int,
    val sunset: Int,
    val temp: Double,
    @SerializedName("feels_like") val feelsLike : Double,
    val pressure: Int,
    val humidity: Int,
    @SerializedName("dew_point") val dewPoint : Double,
    val uvi: Double,
    val clouds: Int,
    val visibility: Int,
    @SerializedName("wind_speed") val windSpeed : Double,
    @SerializedName("wind_deg") val windDeg : Int,
    @SerializedName("wind_gust") val windGust : Double,
    val weather: List<Weather>,
    val minutely: List<Minutely>,
    val hourly: List<Hourly>,
    val daily: List<Daily>,
    val alerts: List<Alerts>?
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Minutely(
    val dt: Int,
    val precipitation: Int
)

data class Hourly(
    val dt: Int,
    val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    val pressure: Int,
    val humidity: Int,
    @SerializedName("dew_point") val dewPoint: Double,
    val uvi: Double,
    val clouds: Int,
    val visibility: Int,
    @SerializedName("wind_speed") val windSpeed: Double,
    @SerializedName("wind_deg") val windDeg: Int,
    @SerializedName("wind_gust") val windGust: Double,
    val weather: List<Weather>,
    val pop: Double,
)

data class Daily(
    val dt: Int,
    val sunrise: Int,
    val sunset: Int,
    val moonrise: Int,
    val moonset: Int,
    @SerializedName("moon_phase") val moonPhase: Double,
    @SerializedName("summary") val summary: String,
    val temp: Temp,
    @SerializedName("feels_like") val feelsLike: FeelsLike,
    val pressure: Int,
    val humidity: Int,
    @SerializedName("dew_point") val dewPoint: Double,
    @SerializedName("wind_speed") val windSpeed: Double,
    @SerializedName("wind_deg") val windDeg: Int,
    @SerializedName("wind_gust") val windGust: Double,
    val weather: List<Weather>,
    val clouds: Int,
    val pop: Double,
    val rain: Double?,
    val uvi: Double,
)

data class FeelsLike(
    val day: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)

data class Temp(
    val day: Double,
    val min: Double,
    val max: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)

data class Alerts(
    @SerializedName("sender_name") val senderName: String,
    val event: String,
    val start: Int,
    val end: Int,
    val description: String
)
