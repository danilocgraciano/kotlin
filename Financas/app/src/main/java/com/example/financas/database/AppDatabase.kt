package com.example.financas.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.example.financas.database.dao.TransacaoDao
import com.example.financas.model.Transacao

@Database(entities = arrayOf(Transacao::class), version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun transacaoDao(): TransacaoDao

}