package com.example.climamundial

import com.example.climamundial.data.dtos.Alerts
import com.example.climamundial.data.dtos.Current
import com.example.climamundial.data.dtos.CurrentWeatherDto
import com.example.climamundial.data.dtos.Daily
import com.example.climamundial.data.dtos.FeelsLike
import com.example.climamundial.data.dtos.Hourly
import com.example.climamundial.data.dtos.Minutely
import com.example.climamundial.data.dtos.Temp
import com.example.climamundial.data.dtos.Weather
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.GlobalContext
import org.koin.core.context.stopKoin
import kotlin.test.assertEquals

class CurrentWeatherDtoTest {
    lateinit var dummyCurrentWeatherDto: CurrentWeatherDto

    @Before
    fun setUp() {
        dummyCurrentWeatherDto =
            CurrentWeatherDto(
                lat = 33.2,
                lon = 22.1,
                timeZone = "10:00",
                timeZoneOffset = 2,
                current = Current(
                    dt = 1684929490,
                    sunrise = 1684926645,
                    sunset = 1684977332,
                    temp = 292.55,
                    feelsLike = 292.87,
                    pressure = 1014,
                    humidity = 89,
                    dewPoint = 290.69,
                    uvi = 0.16,
                    clouds = 53,
                    visibility = 10000,
                    windSpeed = 3.13,
                    windDeg = 93,
                    windGust = 6.71,
                    weather = listOf(
                        Weather(
                            id = 803,
                            main = "Clouds",
                            description = "broken clouds",
                            icon = "04d"
                        )
                    ),
                    minutely = listOf(
                        Minutely(
                            dt = 1684929540,
                            precipitation = 0
                        )
                    ),
                    hourly = listOf(
                        Hourly(
                            dt = 1684926000,
                            temp = 292.01,
                            feelsLike = 292.33,
                            pressure = 1014,
                            humidity = 91,
                            dewPoint = 290.51,
                            uvi = 0.0,
                            clouds = 54,
                            visibility = 10000,
                            windSpeed = 2.58,
                            windDeg = 86,
                            windGust = 5.88,
                            weather = listOf(
                                Weather(
                                    id = 803,
                                    main = "Clouds",
                                    description = "broken clouds",
                                    icon = "04n"
                                )
                            ),
                            pop = 0.15
                        )
                    ),
                    daily = listOf(
                        Daily(
                            dt = 1684951200,
                            sunrise = 1684926645,
                            sunset = 1684977332,
                            moonrise = 1684941060,
                            moonset = 1684905480,
                            moonPhase = 0.16,
                            summary = "Expect a day of partly cloudy with rain",
                            temp = Temp(
                                day = 299.03,
                                min = 290.69,
                                max = 300.35,
                                night = 291.45,
                                eve = 297.51,
                                morn = 292.55
                            ),
                            feelsLike = FeelsLike(
                                day = 299.21,
                                night = 291.37,
                                eve = 297.86,
                                morn = 292.87
                            ),
                            pressure = 1016,
                            humidity = 59,
                            dewPoint = 290.48,
                            windSpeed = 3.98,
                            windDeg = 76,
                            windGust = 8.92,
                            weather = listOf(
                                Weather(
                                    id = 500,
                                    main = "Rain",
                                    description = "light rain",
                                    icon = "10d"
                                )
                            ),
                            clouds = 92,
                            pop = 0.47,
                            rain = 0.15,
                            uvi = 9.23
                        )
                    ),
                    alerts = listOf(
                        Alerts(
                            senderName = "NWS Philadelphia - Mount Holly (New Jersey, Delaware, Southeastern Pennsylvania)",
                            event = "Small Craft Advisory",
                            start = 1684952747,
                            end = 1684988747,
                            description = "...SMALL CRAFT ADVISORY REMAINS IN EFFECT FROM 5 PM THIS AFTERNOON TO 3 AM EST FRIDAY...\n* WHAT...North winds 15 to 20 kt with gusts up to 25 kt and seas 3 to 5 ft expected.\n* WHERE...Coastal waters from Little Egg Inlet to Great Egg Inlet NJ out 20 nm, Coastal waters from Great Egg Inlet to Cape May NJ out 20 nm and Coastal waters from Manasquan Inlet to Little Egg Inlet NJ out 20 nm."
                        )
                    )
                ) )
    }

    @After
    fun tearDown() {

    }


    @Test
    fun `varificando la latitud de Caracas`() {
        assertEquals(33.2, dummyCurrentWeatherDto.lat )
    }

    @Test
    fun `varificando la longitud de Caracas`() {
        assertEquals(22.1, dummyCurrentWeatherDto.lon )
    }
 }