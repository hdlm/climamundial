package com.example.climamundial

import com.example.climamundial.data.dtos.WeatherDto
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class CurrentWeatherDtoTest {
    lateinit var dummyCurrentWeatherDto: WeatherDto

    @Before
    fun setUp()  {
        //TODO implementar el setup del test
    }

    @After
    fun tearDown() {
        //TODO implementar el tearDown del test
    }


    @Test
    fun `verificando la latitud de Caracas`() {
        //TODO implementar la prueba
//        assertEquals(33.2, dummyCurrentWeatherDto.lat )
    }

    @Test
    fun `verificando la longitud de Caracas`() {
        //TODO implementar la prueba
//        assertEquals(22.1, dummyCurrentWeatherDto.lon )
    }
 }