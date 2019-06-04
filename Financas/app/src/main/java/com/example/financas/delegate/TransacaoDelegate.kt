package com.example.financas.delegate

import com.example.financas.model.Transacao

interface TransacaoDelegate {

    fun delegate(transacao: Transacao)

    fun cancel()
}