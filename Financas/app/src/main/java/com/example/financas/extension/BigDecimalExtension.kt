package com.example.financas.extension

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

fun BigDecimal.format(locale: Locale = Locale("pt", "br")): String {
    val currencyInstance: NumberFormat = DecimalFormat.getCurrencyInstance(locale)
    val symbol: String = currencyInstance.currency.symbol
    return currencyInstance.format(this)
        .replace("-${symbol}", "${symbol}-")
        .replace(symbol, "${symbol} ")

}