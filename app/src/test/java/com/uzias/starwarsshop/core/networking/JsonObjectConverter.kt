package com.uzias.starwarsshop.core.networking

import com.google.gson.GsonBuilder
import java.lang.reflect.Type;

object JsonObjectConverter {

    private val gson = GsonBuilder()
            .create()

    fun <T> convertFromJson(json: String, jsonObjectClass: Class<T>): T {
        return gson.fromJson(json, jsonObjectClass)
    }

    fun <T> convertArrayFromJson(json: String, type: Type): List<T> {
        return gson.fromJson<List<T>>(json, type)
    }

}