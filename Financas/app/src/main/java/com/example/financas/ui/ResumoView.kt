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
    private val context: Context,
    private val view: View,
    transacoes: List<Transacao>
) {

    private val resumo: Resumo = Resumo(transacoes)

    fun adicionaReceita() {
        view.resumo_card_receita.setTextColor(ContextCompat.getColor(context, R.color.receita))
        view.resumo_card_receita.text = resumo.receita().format()
    }

    fun adicionaDespesa() {
        view.resumo_card_despesa.setTextColor(ContextCompat.getColor(context, R.color.despesa))
        view.resumo_card_despesa.text = resumo.despesa().format()
    }

    fun adicionaTotal() {
        val total = resumo.total()
        if (total.compareTo(BigDecimal.ZERO) >= 0)
            view.resumo_card_total.setTextColor(ContextCompat.getColor(context, R.color.receita))
        else
            view.resumo_card_total.setTextColor(ContextCompat.getColor(context, R.color.despesa))

        view.resumo_card_total.text = total.format()
    }

}