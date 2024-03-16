package com.reviro.weather.utils

import android.icu.util.Calendar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt


object DataFormatter {

    fun getWeekday(unixTimeStamp: Long): String {
        val date = Date(unixTimeStamp * 1000L)
        val calendar = Calendar.getInstance()
        calendar.time = date
        val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        return dayFormat.format(calendar.time)
    }

    fun getMonthAndYear(unixTimeStamp: Long): String {
        val date = Date(unixTimeStamp * 1000L)
        val calendar = Calendar.getInstance()
        calendar.time = date
        val dayFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return dayFormat.format(calendar.time)
    }

    fun getHours(unixTimeStamp: Long): String {
        val date = Date(unixTimeStamp * 1000L)
        val dayFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return dayFormat.format(date)
    }

    fun convertToKmPerHour(value: Double): Double {
        val kmPerHour = (value * 18) / 5
        return (kmPerHour * 10.0).roundToInt() / 10.0
    }

}