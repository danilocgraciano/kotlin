package com.example.financas.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.math.BigDecimal
import java.util.*

@Entity
class Transacao(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
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