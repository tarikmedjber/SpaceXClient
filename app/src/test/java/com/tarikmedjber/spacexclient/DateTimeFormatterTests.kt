package com.tarikmedjber.spacexclient

import com.tarikmedjber.spacexclient.utils.DateTimeFormatter
import org.junit.Assert
import org.junit.Test

class DateTimeFormatterTests {

    private val firstUtc = "2020-03-23T16:32:22.000Z"
    private val dayAfterFirstUtc = "2020-03-24T16:32:22.000Z"

    @Test
    fun `Should return a date`() {
        val expected = "2020-03-23"
        val actual = DateTimeFormatter.getDate(firstUtc)
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `Should return a time`() {
        val expected = "16:32:22"
        val actual = DateTimeFormatter.getTime(firstUtc)
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `Should return days since certain date`() {
        val expected = 1L
        val actual = DateTimeFormatter.getDaysSince(firstUtc, dayAfterFirstUtc)
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `Should return days from certain date`() {
        val expected = -1L
        val actual = DateTimeFormatter.getDaysSince(dayAfterFirstUtc, firstUtc)
        Assert.assertEquals(expected, actual)
    }

}