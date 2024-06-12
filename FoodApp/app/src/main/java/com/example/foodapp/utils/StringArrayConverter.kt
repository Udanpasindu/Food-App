package com.example.foodapp.utils

import androidx.room.TypeConverter

class StringArrayConverter {
    @TypeConverter
    fun fromString(value: String?): ArrayList<String>? {
        return value?.split(",")?.map { it.trim() }?.toCollection(ArrayList())
    }

    @TypeConverter
    fun toString(value: ArrayList<String>?): String? {
        return value?.joinToString(",")
    }
}
