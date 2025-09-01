package com.example.lkwangsit.util

import android.os.Build
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

object DateUtil {

    private val outputFormatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())

    /**
     * Convert ISO-8601 string (e.g. "2023-09-29T13:00:01Z") to epoch millis.
     */
    fun String.toEpochMillis(): Long {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Instant.parse(this).toEpochMilli()
            } else {
                val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
                parser.timeZone = TimeZone.getTimeZone("UTC")
                parser.parse(this)?.time ?: 0L
            }
        } catch (e: Exception) {
            0L
        }
    }

    /**
     * Convert epoch millis to a formatted date string ("MM/dd/yyyy").
     */
    fun Long.toFormattedDate(): String {
        return try {
            outputFormatter.format(Date(this))
        } catch (e: Exception) {
            this.toString()
        }
    }
}
