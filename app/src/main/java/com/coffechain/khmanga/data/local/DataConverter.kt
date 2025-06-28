package com.coffechain.khmanga.data.local

import androidx.room.TypeConverter
import com.coffechain.khmanga.domain.model.Booth
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataConverter {

    private val gson = Gson()

    // Converter untuk List<String>
    @TypeConverter
    fun fromStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun toStringList(list: List<String>): String {
        return gson.toJson(list)
    }

    // Converter untuk List<Booth>
    @TypeConverter
    fun fromBoothList(value: String): List<Booth> {
        val listType = object : TypeToken<List<Booth>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun toBoothList(list: List<Booth>): String {
        return gson.toJson(list)
    }
}