package com.example.climamundial.networking

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitHelperImpl : RetrofitHelper {

    private fun provideNetworkURL(): String = BASE_URL

    private fun provideHttpLogger(): HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(
        HttpLoggingInterceptor.Level.BODY)

    private fun provideOKHttp(logger: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()

        // see: SocketTimeoutException
        with(okHttpClient) {
            addInterceptor(logger)
            callTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
        }
        return okHttpClient.build()
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(provideNetworkURL())
        .client(
            provideOKHttp( provideHttpLogger() )
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitWithoutConverter = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .build()


    override fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }

    override fun <T> buildServiceWithoutConverter(service: Class<T>): T {
        return retrofitWithoutConverter.create(service)
    }

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/data/3.0/"
        const val API_KEY = "55bca60ac535293ccbcf135720e7d8b6"
        private const val REQUEST_TIMEOUT = 60L

    }
}