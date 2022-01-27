package com.example.stackexchange.util

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {
    fun millisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        return formatter.format(Date(millis))
    }
}