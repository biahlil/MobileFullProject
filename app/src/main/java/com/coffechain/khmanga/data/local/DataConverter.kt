package com.coffechain.khmanga.data.local

import androidx.room.TypeConverter
import com.coffechain.khmanga.domain.model.Booth
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataConverter {

    private val gson = Gson()

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

    @TypeConverter
    fun fromStringList(value: String?): List<String> {
        if (value == null) {
            return emptyList()
        }
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun toStringList(list: List<String>?): String {
        return gson.toJson(list ?: "")
    }

    // Converter untuk List<Int> (untuk availableVolumes)
    @TypeConverter
    fun fromIntList(value: String?): List<Int> {
        if (value == null) {
            return emptyList()
        }
        val listType = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun toIntList(list: List<Int>?): String {
        return gson.toJson(list ?: "")
    }
}