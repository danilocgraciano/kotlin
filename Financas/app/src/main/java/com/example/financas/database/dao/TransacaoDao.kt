package com.example.financas.database.dao

import android.arch.persistence.room.*
import com.example.financas.model.Transacao

@Dao
interface TransacaoDao {

    @Query("SELECT * FROM transacao")
    fun all(): List<Transacao>

    @Insert
    fun add(vararg transacao: Transacao)

    @Update
    fun edit(transacao: Transacao)

    @Delete
    fun remove(transacao: Transacao)

    @Query("SELECT * FROM transacao WHERE id = :id")
    fun getById(id: Long): Transacao
}