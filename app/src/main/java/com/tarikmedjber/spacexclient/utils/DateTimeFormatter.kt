package com.tarikmedjber.spacexclient.utils

import com.tarikmedjber.spacexclient.utils.DateTimeFormatter.localDate
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

object DateTimeFormatter {

    private fun String.dateTime(): Date? {
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.UK)
            .parse(this)
    }

    private fun String.localDate(): LocalDate? {
        return LocalDate.parse(
            this, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

        )
    }

    fun getDate(utc: String): String? {
        return utc.dateTime()
            ?.let { SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(it) }
    }

    fun getTime(utc: String): String? {
        return utc.dateTime()?.let { SimpleDateFormat("HH:mm:ss", Locale.UK).format(it) }
    }

    fun getDaysSince(firstUTC: String, secondUTC: String? = null): Long {
        val dateOfLaunch = firstUTC.localDate()
        val dateNow = secondUTC?.localDate() ?: LocalDate.now()
        return ChronoUnit.DAYS.between(dateOfLaunch, dateNow)
    }


}