package com.jmrsa.moviecrudapp.utils

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Locale

fun formatMinutesToHoursAndMinutes(timeInMinutes: Int): String {
    val hours = timeInMinutes / 60
    val minutes = timeInMinutes % 60
    return String.format(Locale.getDefault(), "%dh %d min", hours, minutes)
}

fun formatDate(
    dateString: String,
    originalDateFormat: String = "yyyy-MM-dd",
    dateFormat: String = "dd.MM.yyyy",
): String {
    if (dateString.isEmpty()) return ""

    val parser = SimpleDateFormat(originalDateFormat, Locale.getDefault()).parse(dateString)!!
    val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
    return formatter.format(parser).orEmpty()
}

fun formatCurrency(amount: Double): String {
    val decimalFormat = DecimalFormat("$#,###.###")
    return decimalFormat.format(amount)
}

fun formatRating(rating: Double): String {
    return "%.02f".format(rating)
}

fun formatFullLanguageName(languageCode: String) : String {
    if (languageCode.isEmpty()) return ""

    val locale = Locale(languageCode)
    return locale.getDisplayLanguage(locale).replaceFirstChar { it.uppercase() }
}


