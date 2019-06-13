package com.example.financas.dao

import com.example.financas.model.Transacao

class OldTransacaoDao {

    val transacoes: List<Transacao> = Companion.transacoes

    //deixa objetos de forma estática
    //@JvmStatic
    companion object {
        private val transacoes: MutableList<Transacao> = mutableListOf()
    }

    fun add(transacao: Transacao) {
        Companion.transacoes.add(transacao)
    }

    fun edit(transacao: Transacao, index: Int) {
        Companion.transacoes[index] = transacao
    }

    fun remove(index: Int) {
        Companion.transacoes.removeAt(index)
    }
}