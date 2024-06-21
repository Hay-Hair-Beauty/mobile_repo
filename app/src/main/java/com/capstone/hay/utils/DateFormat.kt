package com.capstone.hay.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun formatTimestamp(jsonTimestamp: Map<String, Int>): String {
    val seconds = jsonTimestamp["_seconds"]!!
    val nanoseconds = jsonTimestamp["_nanoseconds"]!!
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    calendar.timeInMillis = (seconds * 1000 + nanoseconds / 1_000_000).toLong()
    val formatter = java.text.SimpleDateFormat("d MMM yyyy", Locale.forLanguageTag("id"))
    return formatter.format(calendar.time)
}

fun formatDate(timestamp: Long): String {
    val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    val date = Date(timestamp)
    return dateFormat.format(date)
}