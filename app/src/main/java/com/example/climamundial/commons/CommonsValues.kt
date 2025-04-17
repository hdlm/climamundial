package com.example.climamundial.commons

import android.content.Context

object CommonsValues {
    enum class TypeActivity(val value: String){
        LOCATION("location"),
        DATA("data")
    }

    lateinit var context: Context
}