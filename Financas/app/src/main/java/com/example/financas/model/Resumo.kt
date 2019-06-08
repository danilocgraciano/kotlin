package com.example.financas.model

import java.math.BigDecimal

class Resumo(private val transacoes: List<Transacao>) {

    val receita: BigDecimal
        get() = totalizaPor(Tipo.RECEITA)

    val despesa: BigDecimal
        get() = totalizaPor(Tipo.DESPESA)

    val total: BigDecimal
        get() = receita.subtract(despesa)

    private fun totalizaPor(tipo: Tipo): BigDecimal {

        return BigDecimal(
            transacoes
                .filter({ transacao -> transacao.tipo == tipo })
                .sumByDouble({ transacao -> transacao.valor.toDouble() })
        )

    }
}