package com.example.financas.extension

import java.text.SimpleDateFormat
import java.util.Calendar

fun Calendar.format(pattern: String = "dd/MM/yyyy"): String {
    return SimpleDateFormat(pattern).format(this.time)
}