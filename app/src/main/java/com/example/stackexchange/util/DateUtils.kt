package com.example.stackexchange.util

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {
    fun millisToDate(millis: Long) {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val dateString = formatter.format(Date(millis))
    }
}