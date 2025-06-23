package com.example.climamundial

import com.example.climamundial.data.dtos.City
import com.example.climamundial.data.dtos.Clouds
import com.example.climamundial.data.dtos.Coord
import com.example.climamundial.data.dtos.Main
import com.example.climamundial.data.dtos.Rain
import com.example.climamundial.data.dtos.Weather
import com.example.climamundial.data.dtos.WeatherDto
import com.example.climamundial.data.dtos.WeatherList
import com.example.climamundial.data.dtos.WeatherSys
import com.example.climamundial.data.dtos.Wind
import com.example.climamundial.data.repositories.ClimaRepository
import com.example.climamundial.presentation.usecase.ClimaInfoUseCase
import com.example.climamundial.presentation.usecase.ClimaInfoUseCaseImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

@OptIn(ExperimentalCoroutinesApi::class)
class ClimaInfoUseCaseTest : KoinTest {
    private val testDispatcher = StandardTestDispatcher()
    private val climaInfoUseCase: ClimaInfoUseCase by inject()

    // We will mock the repository directly, encapsulating all scenarios within it
    private lateinit var mockClimaRepository: ClimaRepository

    @Before
    fun setUp() {
        mockClimaRepository = mockk()

        startKoin {
            modules(
                module {
                    single<ClimaRepository> { mockClimaRepository }
                    single<ClimaInfoUseCase> { ClimaInfoUseCaseImpl() }
                }
            )
        }
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
        stopKoin()
    }

    @Test
    fun `test fetching Weather through ClimaInfoUseCase and succeeds`() = runTest {
        //<editor-fold desc="mock data" defaultstate="collapsed">
        val cityObject = City(
            id = 3163858,
            name = "Zocca",
            coord = Coord(lat = 44.34, lon = 10.99),
            country = "IT",
            population = 4593,
            timezone = 7200,
            sunrise = 1750563128,
            sunset = 1750619033
        )

        val weatherList1 = WeatherList(
            dt = 1750636800,
            main = Main(
                temp = 19.12,
                feelsLike = 19.18,
                tempMin = 19.12,
                tempMax = 20.01,
                pressure = 1020,
                seaLevel = 1020,
                grndLevel = 953,
                humidity = 80,
                tempKf = -0.89
            ),
            weather = listOf(
                Weather(id = 800, main = "Clear", description = "clear sky", icon = "01n")
            ),
            clouds = Clouds(all = 1),
            wind = Wind(speed = 2.45, deg = 217, gust = 1.93),
            visibility = 10000,
            pop = 0.0,
            sys = WeatherSys(pod = "n"),
            dtTxt = "2025-06-23 00:00:00",
            rain = null
        )

        val weatherList2 = WeatherList(
            dt = 1750647600,
            main = Main(
                temp = 18.64,
                feelsLike = 18.54,
                tempMin = 18.63,
                tempMax = 18.64,
                pressure = 1019,
                seaLevel = 1019,
                grndLevel = 952,
                humidity = 76,
                tempKf = 0.01
            ),
            weather = listOf(
                Weather(id = 800, main = "Clear", description = "clear sky", icon = "01n")
            ),
            clouds = Clouds(all = 10),
            wind = Wind(speed = 2.72, deg = 210, gust = 2.03),
            visibility = 10000,
            pop = 0.0,
            sys = WeatherSys(pod = "n"),
            dtTxt = "2025-06-23 03:00:00",
            rain = null
        )

        val weatherList3 = WeatherList(
            dt = 1750658400,
            main = Main(
                temp = 21.7,
                feelsLike = 21.6,
                tempMin = 21.7,
                tempMax = 21.7,
                pressure = 1019,
                seaLevel = 1019,
                grndLevel = 952,
                humidity = 64,
                tempKf = 0.0
            ),
            weather = listOf(
                Weather(id = 800, main = "Clear", description = "clear sky", icon = "01d")
            ),
            clouds = Clouds(all = 9),
            wind = Wind(speed = 1.42, deg = 199, gust = 1.85),
            visibility = 10000,
            pop = 0.0,
            sys = WeatherSys(pod = "d"),
            dtTxt = "2025-06-23 06:00:00",
            rain = null
        )

        val weatherList4 = WeatherList(
            dt = 1750669200,
            main = Main(
                temp = 26.78,
                feelsLike = 26.9,
                tempMin = 26.78,
                tempMax = 26.78,
                pressure = 1018,
                seaLevel = 1018,
                grndLevel = 952,
                humidity = 44,
                tempKf = 0.0
            ),
            weather = listOf(
                Weather(id = 801, main = "Clouds", description = "few clouds", icon = "02d")
            ),
            clouds = Clouds(all = 13),
            wind = Wind(speed = 0.37, deg = 87, gust = 1.6),
            visibility = 10000,
            pop = 0.0,
            sys = WeatherSys(pod = "d"),
            dtTxt = "2025-06-23 09:00:00",
            rain = null
        )

        val weatherList5 = WeatherList(
            dt = 1750690800, // Este es el sexto elemento en tu JSON, lo usé para tener uno con 'rain'
            main = Main(
                temp = 29.89,
                feelsLike = 29.68,
                tempMin = 29.89,
                tempMax = 29.89,
                pressure = 1015,
                seaLevel = 1015,
                grndLevel = 949,
                humidity = 41,
                tempKf = 0.0
            ),
            weather = listOf(
                Weather(id = 500, main = "Rain", description = "light rain", icon = "10d")
            ),
            clouds = Clouds(all = 15),
            wind = Wind(speed = 0.68, deg = 133, gust = 1.61),
            visibility = 10000,
            pop = 0.2,
            sys = WeatherSys(pod = "d"),
            dtTxt = "2025-06-23 15:00:00",
            rain = Rain(threeH = 0.14) // Ejemplo con lluvia
        )

        val mockWeatherDto = WeatherDto(
            cod = "200",
            message = 0,
            cnt = 5, // Cambiado a 5 para reflejar la cantidad de elementos en la lista
            list = listOf(weatherList1, weatherList2, weatherList3, weatherList4, weatherList5),
            city = cityObject
        )
        //</editor-fold>

        coEvery { mockClimaRepository.fetchCurrentWeather(
            latitud = 44.34,
            longitud = 10.99,
            units = "metric"
        ) } returns Result.success(mockWeatherDto)

        val result = climaInfoUseCase.invoke(
            latitud = 44.34,
            longitud = 10.99,
            units ="metric"
        ).first()

        assertTrue("Result should be successful", result.isSuccess)

        val weater = result.getOrThrow()
    }

    @Test
    fun `test fetching Weather through ClimaInfoUseCase and fails`() = runTest {
        val expectedErrorMessage = "Simulated general failure"
        val expectedException = Exception(expectedErrorMessage)

        coEvery { mockClimaRepository.fetchCurrentWeather(
            latitud = 44.34,
            longitud = 10.99,
            units = "metric"
        ) } returns Result.failure(expectedException)

        val result = climaInfoUseCase.invoke(
            latitud = 44.34,
            longitud = 10.99,
            units = "metric"
        ).first()

        assertTrue("Result should be a failure", result.isFailure)

        val actualException = result.exceptionOrNull()
        assertEquals("Exception message should match expected", expectedErrorMessage, actualException?.message)
    }

}