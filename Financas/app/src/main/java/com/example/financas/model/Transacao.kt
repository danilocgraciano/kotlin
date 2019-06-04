package com.example.financas.model

import java.math.BigDecimal
import java.util.*

class Transacao(
    val valor: BigDecimal,
    val tipo: Tipo,
    val categoria: String = "Indefinida",
    val data: Calendar = Calendar.getInstance()
)
//{
//    constructor(valor: BigDecimal, tipo: Tipo) : this(valor, "Indefinida", tipo)
//}

//backing fields
//var valor: BigDecimal = valor
//set(value) {
//    field = value
//}
//get() {
//    return field
//}