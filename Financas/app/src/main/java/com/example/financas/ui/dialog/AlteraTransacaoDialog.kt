package com.example.financas.ui.dialog

import android.content.Context
import android.view.ViewGroup
import com.example.financas.R
import com.example.financas.delegate.TransacaoDelegate
import com.example.financas.extension.format
import com.example.financas.model.Tipo
import com.example.financas.model.Transacao

class AlteraTransacaoDialog(
    private val context: Context,
    viewGroup: ViewGroup
) : FormTransacaoDialog(context, viewGroup) {

    override val tituloBtnPositivo: String
        get() = "Alterar"

    override fun tituloPor(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA)
            return R.string.altera_receita
        return R.string.altera_despesa
    }

    fun show(transacao: Transacao, transacaoDelegate: TransacaoDelegate) {

        val tipo = transacao.tipo
        super.show(tipo, transacaoDelegate)
        setValues(transacao)

    }

    fun setValues(transacao: Transacao) {
        campoId = transacao.id
        campoValor.setText(transacao.valor.toString())
        campoData.setText(transacao.data.format())
        val categorias = context.resources.getStringArray(categoriasPor(transacao.tipo))
        val index = categorias.indexOf(transacao.categoria)
        campoCategoria.setSelection(index, true)
    }
}