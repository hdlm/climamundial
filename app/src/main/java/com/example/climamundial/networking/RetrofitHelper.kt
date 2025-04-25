package com.example.climamundial.networking

interface RetrofitHelper {
    fun<T> buildService(service: Class<T>) : T
    fun <T> buildServiceWithoutConverter(service: Class<T>): T
}