package com.qot.assignment.utils

import java.text.SimpleDateFormat
import java.util.*


fun String?.formattedDate(): String {
    if (this.isNullOrBlank()) {
        return ""
    }
    val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
    inputDateFormat.timeZone = TimeZone.getTimeZone("UTC")

    try {
        val convertedDate = inputDateFormat.parse(this) ?: return ""
        val outputPattern = "dd-MMM-yyyy"
        val outputDf = SimpleDateFormat(outputPattern, Locale.ENGLISH)
        outputDf.timeZone = TimeZone.getDefault()
        return outputDf.format(convertedDate)
    } catch (e: Exception) {
        return ""
    }

}
