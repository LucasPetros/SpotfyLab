package com.lucas.petros.network.extension


import com.google.gson.Gson
import com.google.gson.JsonElement
import retrofit2.HttpException

fun HttpException.fetchErrorMessage(): String? {
    return try {
        val responseBody = this.response()?.errorBody()?.string()
        val gson = Gson()
        val element: JsonElement = gson.fromJson(responseBody, JsonElement::class.java)
        element.asJsonObject.get("message").asString
    } catch (e: java.lang.Exception) {
        null
    }
}