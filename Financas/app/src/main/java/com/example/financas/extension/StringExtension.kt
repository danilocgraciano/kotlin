package com.example.financas.extension

import java.text.SimpleDateFormat
import java.util.Calendar

fun String.toCalendar(pattern: String = "dd/MM/yyyy"): Calendar {
    val dataConvertida = SimpleDateFormat(pattern).parse(this)
    val data = Calendar.getInstance()
    data.time = dataConvertida
    return data
}