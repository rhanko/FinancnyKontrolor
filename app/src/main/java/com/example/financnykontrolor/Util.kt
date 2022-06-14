package com.example.financnykontrolor

import androidx.core.text.isDigitsOnly
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

val FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")!!


/**
 * Convertor will check if the date is in right format
 */
fun tryDateConvert(dateString: String) : Boolean {
    if (dateString.length != 10) {
        return false
    }

    //if there are "/"
    for (i in 2..5 step 3) {
        if (dateString[i].toString() != "/") {
            return false
        }
    }

    //if month is correct
    val month = dateString.substring(3, 5)
    if (month.isDigitsOnly()) {
        if (month.toInt() !in (1..12)) {return false}
    } else {return false}

    //if year is correct
    val year = dateString.substring(6, 10)
    for (i in 1..4) {
        if (!year.isDigitsOnly()) {return false}
    }

    //if day is correct
    val day = dateString.substring(0, 2)
    if (day.isDigitsOnly() && day.toInt() <= 31 && day.toInt() >= 1) {
        when (day.toShort()) {
            in (31..31) -> if (month.toInt() != (1 or 3 or 5 or 7 or 8 or 10 or 12) ) {return false}
            in (30..30) -> if (month.toInt() == 2) {return false}
            in (29..29) -> if (month.toInt() == 2 && year.toInt().mod(4) == 0) {return false}
        }
    } else {return false}

    return true
}

/**
 * Change date and time to milliseconds
 */
fun getMilliseconds(dateString: String?): Long? {
    if (dateString != null) {
        val date = LocalDateTime.parse(dateString, FORMAT)
        return date.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli()
    }
    return null
}

/**
 * Change milliseconds to date in String
 */
fun getDatefromMilliseconds(milli: Long): String {
    val date = Instant.ofEpochMilli(milli).atZone(ZoneId.systemDefault()).toLocalDateTime()
    return date.format(FORMAT).toString()
}