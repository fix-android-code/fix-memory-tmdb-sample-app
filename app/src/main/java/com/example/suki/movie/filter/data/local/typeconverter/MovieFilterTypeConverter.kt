package com.example.suki.movie.filter.data.local.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MovieFilterTypeConverter {
    // List<String> <-> String
    @TypeConverter
    fun listOfStringToString(list: List<String>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun stringToListOfString(string: String): List<String> {
        return Gson().fromJson(string, object : TypeToken<List<String>>() {}.type)
    }

    @TypeConverter
    fun stringToListOfInt(value: String): List<Int> {
        val type = object: TypeToken<List<Int>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun listOfIntToString(list: List<Int>): String {
        val type = object: TypeToken<List<Int>>() {}.type
        return Gson().toJson(list, type)
    }
}