package com.example.moneysaver.utils

import java.text.SimpleDateFormat
import java.util.*

fun Date.format(): String {
    return SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(this)
}

val Date.calendar: Calendar
    get() = Calendar.getInstance().also { it.time = this }

fun Date.plusMonths(monthCount: Int): Date = calendar.also { it.add(Calendar.MONTH, monthCount) }.time