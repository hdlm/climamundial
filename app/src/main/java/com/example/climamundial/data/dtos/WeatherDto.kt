package com.example.climamundial.data.dtos

import com.google.gson.annotations.SerializedName

data class WeatherDto(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val list: List<WeatherList>,
    val city: City
)

data class WeatherList(
    val dt: Long,
    val main: Main,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Int,
    val pop: Double,
    val sys: WeatherSys,
    @SerializedName("dt_txt")
    val dtTxt: String,
    val rain: Rain? = null // This field is nullable as it might not always be present
)

data class Main(
    val temp: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("temp_min")
    val tempMin: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    val pressure: Int,
    @SerializedName("sea_level")
    val seaLevel: Int,
    @SerializedName("grnd_level")
    val grndLevel: Int,
    val humidity: Int,
    @SerializedName("temp_kf")
    val tempKf: Double
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Clouds(
    val all: Int
)

data class Wind(
    val speed: Double,
    val deg: Int,
    val gust: Double
)

data class WeatherSys( // Renamed to WeatherSys to distinguish from the 'sys' object in City
    val pod: String
)

data class Rain(
    @SerializedName("3h")
    val threeH: Double
)

data class City(
    val id: Int,
    val name: String,
    val coord: Coord,
    val country: String,
    val population: Int,
    val timezone: Int,
    val sunrise: Long,
    val sunset: Long
)

data class Coord(
    val lat: Double,
    val lon: Double
)