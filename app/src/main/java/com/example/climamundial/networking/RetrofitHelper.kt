package com.example.climamundial.networking

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

interface RetrofitHelper {
    fun<T> buildService(service: Class<T>) : T
    fun <T> buildServiceWithoutConverter(service: Class<T>): T
    fun provideOKHttp(logger: HttpLoggingInterceptor): OkHttpClient
    fun provideHttpLogger(): HttpLoggingInterceptor
}