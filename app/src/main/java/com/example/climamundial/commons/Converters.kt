package com.example.climamundial.commons

import androidx.room.TypeConverter
import com.example.climamundial.commons.CommonsValues.TypeActivity

class Converters {
    @TypeConverter
    fun fromTypeActivity(value: TypeActivity): String {
        return value.value
    }

    @TypeConverter
    fun toTypeActivity(value: String): TypeActivity {
        return when (value) {
            "location" -> TypeActivity.LOCATION
            "data" -> TypeActivity.DATA
            else -> throw IllegalArgumentException("Invalid type activity")
        }
    }
}