package com.example.financas.model

import java.math.BigDecimal

class Resumo(private val transacoes: List<Transacao>) {

    fun receita(): BigDecimal {
        return totalizaPor(Tipo.RECEITA)
    }

    fun despesa(): BigDecimal {
        return totalizaPor(Tipo.DESPESA)
    }

    fun total(): BigDecimal {
        return receita().subtract(despesa())
    }

    private fun totalizaPor(tipo: Tipo): BigDecimal {
        var total = BigDecimal.ZERO

        for (transacao in transacoes)
            if (transacao.tipo == tipo)
                total = total.plus(transacao.valor)

        return total
    }
}