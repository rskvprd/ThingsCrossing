package com.app.thingscrossing.feature_advertisement.domain.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDateTime

inline fun <reified T> Gson.fromJson(json: String): T =
    fromJson<T>(json, object : TypeToken<T>() {}.type)

class Converters {
    @TypeConverter
    fun toJsonArrayList(value: ArrayList<String>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun fromJsonArrayList(value: String): ArrayList<String> {
        return try {
            Gson().fromJson<ArrayList<String>>(value) //using extension function
        } catch (e: Exception) {
            arrayListOf()
        }
    }

    @TypeConverter
    fun toJsonMap(value: Map<String, String>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun fromJsonMap(value: String): Map<String, String> {
        return try {
            Gson().fromJson<Map<String, String>>(value)
        } catch (e: Exception) {
            mapOf<String, String>()
        }
    }

    @TypeConverter
    fun toJsonLocalDateTime(value: LocalDateTime): String {
        return Gson().toJson(value.toString())
    }

    @TypeConverter
    fun fromJsonLocalDateTime(value: String): LocalDateTime {
        return Gson().fromJson(value)
    }
}