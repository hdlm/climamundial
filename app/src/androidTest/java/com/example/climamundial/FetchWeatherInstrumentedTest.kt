package com.example.climamundial

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.climamundial.data.dtos.CurrentWeatherDto
import com.example.climamundial.data.repositories.ClimaRepository
import com.example.climamundial.di.Modules
import com.example.climamundial.networking.RetrofitHelperImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.GlobalContext
import org.koin.core.context.GlobalContext.unloadKoinModules
import org.koin.core.context.stopKoin
import retrofit2.Retrofit

@RunWith(AndroidJUnit4::class)
@SmallTest
class FetchWeatherInstrumentedTest : KoinComponent {
    val repository by inject<ClimaRepository>()
    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        GlobalContext.startKoin {
            androidLogger()
            androidContext(ApplicationProvider.getApplicationContext())
            modules(Modules.instrumentedTestModule)
        }

        Dispatchers.setMain(testDispatcher)

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
        unloadKoinModules( Modules.instrumentedTestModule)
        stopKoin()
    }

    @Test
    fun fetchWeatherFromCaracas(): Unit = runTest {
        val scope = CoroutineScope(testDispatcher)
        val latitud = 10.4806
        val longitud = -66.9036
        val apiKey = RetrofitHelperImpl.API_KEY
        var result : CurrentWeatherDto? = null

//        scope.launch {

        runBlocking {
            repository.fetchCurrentWeather(
                latitud,
                longitud,
                apiKey
            )
            result = repository.fetchCurrentWeather(latitud, longitud, apiKey)
        }

//        }

        assert(result != null)


    }


}