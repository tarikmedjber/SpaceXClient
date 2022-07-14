package com.tarikmedjber.spacexclient

import com.tarikmedjber.spacexclient.utils.CurrencyFormatter.asCurrency
import org.junit.Assert
import org.junit.Test

class CurrencyFormatterTest {

    @Test
    fun `Should format a 1 digit number`() {
        val unformattedCurrency = 1
        val expected = "$1.00"
        val actual = unformattedCurrency.asCurrency()
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `Should format a 2 digit number`() {
        val unformattedCurrency = 11
        val expected = "$11.00"
        val actual = unformattedCurrency.asCurrency()
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `Should format a 4 digit number`() {
        val unformattedCurrency = 1120
        val expected = "$1,120.00"
        val actual = unformattedCurrency.asCurrency()
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `Should format a large number`() {
        val unformattedCurrency = 1120000000000033300
        val expected = "$1,120,000,000,000,033,300.00"
        val actual = unformattedCurrency.asCurrency()
        Assert.assertEquals(expected, actual)
    }
}