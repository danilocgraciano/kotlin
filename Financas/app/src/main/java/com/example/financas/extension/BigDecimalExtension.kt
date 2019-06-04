package com.example.financas.extension

import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.Locale

fun BigDecimal.format(locale: Locale = Locale("pt", "br")): String {
    return DecimalFormat.getCurrencyInstance(locale).format(this)
}