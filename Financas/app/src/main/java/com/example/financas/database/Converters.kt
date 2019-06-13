package com.example.financas.database

import android.arch.persistence.room.TypeConverter
import com.example.financas.extension.format
import com.example.financas.extension.toCalendar
import com.example.financas.model.Tipo
import java.math.BigDecimal
import java.util.*

class Converters {

    companion object {

        @TypeConverter
        @JvmStatic
        fun fromBigDecimal(value: BigDecimal): Long {
            return value.toLong()
        }

        @TypeConverter
        @JvmStatic
        fun toBigDecimal(value: Long): BigDecimal {
            return BigDecimal(value)
        }

        @TypeConverter
        @JvmStatic
        fun fromCalendar(value: Calendar): String {
            return value.format()
        }

        @TypeConverter
        @JvmStatic
        fun toCalendar(value: String): Calendar {
            return value.toCalendar()
        }

        @TypeConverter
        @JvmStatic
        fun fromTipo(value: Tipo): String {

            return when (value) {
                Tipo.RECEITA -> "RECEITA"
                Tipo.DESPESA -> "DESPESA"
                else -> "INDEFINIDA"
            }
        }

        @TypeConverter
        @JvmStatic
        fun toTipo(value: String): Tipo {
            return when (value) {
                "RECEITA" -> Tipo.RECEITA
                "DESPESA" -> Tipo.DESPESA
                else -> Tipo.INDEFINIDA
            }
        }

    }
}