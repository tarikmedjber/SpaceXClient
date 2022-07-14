package com.tarikmedjber.spacexclient.utils

import java.text.NumberFormat
import java.util.*

object CurrencyFormatter {

    private val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US).configure()

    private fun NumberFormat.configure() = apply {
        maximumFractionDigits = 2
        minimumFractionDigits = 2
    }
    fun Number.asCurrency(): String {
        return currencyFormatter.format(this)
    }
}

