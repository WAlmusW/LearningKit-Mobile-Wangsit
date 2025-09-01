package com.example.libs.util

import com.google.gson.JsonParseException
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.TimeoutException

object Util {
    fun <T> handleApiError(exception: Throwable): Response<T> {
        val message = when (exception) {
            is TimeoutException -> "Request timed out. Please try again."
            is IOException -> "Network error. Please check your connection."
            is JsonParseException -> "Malformed JSON received. Parsing failed."
            is IllegalArgumentException -> "Invalid argument provided. ${exception.message}"
            is IllegalStateException -> "Illegal application state. ${exception.message}"
            else -> "Unexpected error occurred: ${exception.message}"
        }
        return Response.error(500, message.toResponseBody())
    }
}