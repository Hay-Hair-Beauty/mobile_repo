package com.capstone.hay.utils

import org.junit.Assert.*
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateFormatKtTest{
    @Test
    fun `test formatDate with valid timestamp`() {
        val timestamp = 1624291200000L

        val formattedDate = formatDate(timestamp)

        val expectedFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

        val expectedDate = Date(timestamp)
        val expectedDateString = expectedFormat.format(expectedDate)

        assertEquals(expectedDateString, formattedDate)
    }

    @Test
    fun `test formatDate with zero timestamp`() {

        val timestamp = 0L

        val formattedDate = formatDate(timestamp)

        val expectedFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

        val expectedDate = Date(timestamp)
        val expectedDateString = expectedFormat.format(expectedDate)

        assertEquals(expectedDateString, formattedDate)
    }

    @Test
    fun `test formatDate with negative timestamp`() {

        val timestamp = -1624291200000L


        val formattedDate = formatDate(timestamp)

        val expectedFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

        val expectedDate = Date(timestamp)
        val expectedDateString = expectedFormat.format(expectedDate)

        assertEquals(expectedDateString, formattedDate)
    }
}