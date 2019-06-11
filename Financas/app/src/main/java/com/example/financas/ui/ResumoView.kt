package com.example.financas.ui

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import com.example.financas.R
import com.example.financas.extension.format
import com.example.financas.model.Resumo
import com.example.financas.model.Transacao
import kotlinx.android.synthetic.main.resumo_card.view.*
import java.math.BigDecimal

class ResumoView(
    context: Context,
    private val view: View?,
    transacoes: List<Transacao>
) {

    private val resumo: Resumo = Resumo(transacoes)
    private val corReceita: Int = ContextCompat.getColor(context, R.color.receita)
    private val corDespesa: Int = ContextCompat.getColor(context, R.color.despesa)

    fun atualiza() {
        adicionaReceita()
        adicionaDespesa()
        adicionaTotal()
    }

    private fun adicionaReceita() {
        view?.let {
            with(view.resumo_card_receita) {
                setTextColor(corReceita)
                text = resumo.receita.format()
            }
        }
    }

    private fun adicionaDespesa() {
        view?.let {
            with(view.resumo_card_despesa) {
                setTextColor(corDespesa)
                text = resumo.despesa.format()
            }
        }
    }

    private fun adicionaTotal() {
        val total = resumo.total
        val cor = corPor(total)
        view?.let {
            with(view.resumo_card_total) {
                setTextColor(cor)
                text = total.format()
            }
        }
    }

    private fun corPor(total: BigDecimal): Int {
        if (total >= BigDecimal.ZERO)
            return corReceita
        return corDespesa
    }

}