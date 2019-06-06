package com.example.financas.ui.dialog

import android.content.Context
import android.view.ViewGroup
import com.example.financas.R
import com.example.financas.model.Tipo

class AdicionaTransacaoDialog(
    context: Context,
    viewGroup: ViewGroup
) : FormTransacaoDialog(context, viewGroup) {

    override val tituloBtnPositivo: String
        get() = "Adicionar"

    override fun tituloPor(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA)
            return R.string.adiciona_receita
        return R.string.adiciona_despesa
    }

}