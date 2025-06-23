package com.example.climamundial

import com.example.climamundial.data.repositories.ClimaRepository
import com.example.climamundial.data.repositories.ClimaRepositoryImpl
import com.example.climamundial.networking.RetrofitHelper
import com.example.climamundial.networking.RetrofitHelperImpl
import com.example.climamundial.presentation.usecase.ClimaInfoUseCase
import com.example.climamundial.presentation.usecase.ClimaInfoUseCaseImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.Test
import kotlin.test.assertEquals

class ClimaInfoUseCaseNetworkTest : KoinTest {

    private val testDispatcher = StandardTestDispatcher()
    private val climaInfoUseCase: ClimaInfoUseCase by inject()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        startKoin {
            modules(
                module {
                    single<RetrofitHelper> { RetrofitHelperImpl() }
                    single<ClimaRepository> { ClimaRepositoryImpl() }
                    single<ClimaInfoUseCase> { ClimaInfoUseCaseImpl() }
                }
            )
        }
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
        stopKoin()
    }

    @Test
    fun `test fetching Weather through a Network success`() = runTest {
        val expectedCityName = "Zocca"

        val result = climaInfoUseCase.invoke(
            latitud = 44.34,
            longitud = 10.99,
            units = "metric"
        ).first()

        assertTrue("Result should be successful (real network call)", result.isSuccess)

        val clima = result.getOrThrow()

        assertEquals( message = "City name should be the same", expected = expectedCityName, actual = clima.city.name)
    }

}