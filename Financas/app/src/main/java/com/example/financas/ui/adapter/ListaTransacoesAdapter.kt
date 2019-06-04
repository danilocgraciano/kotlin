package com.example.financas.ui.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.financas.R
import com.example.financas.extension.format
import com.example.financas.model.Tipo
import com.example.financas.model.Transacao
import kotlinx.android.synthetic.main.transacao_item.view.*

class ListaTransacoesAdapter(
    private val context: Context,
    private val transacoes: List<Transacao>
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.transacao_item, parent, false)

        val transacao = getItem(position)

        setValor(view, transacao)
        setIcone(view, transacao)
        setCategoria(view, transacao)
        setData(view, transacao)

        return view
    }

    private fun setValor(view: View, transacao: Transacao) {
        view.transacao_valor.text = transacao.valor.format()
        view.transacao_valor.setTextColor(ContextCompat.getColor(context, R.color.despesa))
        if (transacao.tipo == Tipo.RECEITA)
            view.transacao_valor.setTextColor(ContextCompat.getColor(context, R.color.receita))
    }

    private fun setIcone(view: View, transacao: Transacao) {
        view.transacao_icone.setBackgroundResource(R.drawable.icone_transacao_item_despesa)
        if (transacao.tipo == Tipo.RECEITA)
            view.transacao_icone.setBackgroundResource(R.drawable.icone_transacao_item_receita)
    }

    private fun setCategoria(view: View, transacao: Transacao) {
        view.transacao_categoria.text = resumir(transacao.categoria)
    }

    private fun setData(view: View, transacao: Transacao) {
        view.transacao_data.text = transacao.data.format();
    }


    override fun getItem(index: Int): Transacao {
        return transacoes[index]
    }

    override fun getItemId(p0: Int): Long {
        return -1
    }

    override fun getCount(): Int {
        return transacoes.size
    }

    fun resumir(text: String): String {
        if (text.length > 14)
            return "${text.substring(0, 14)}..."
        return text
    }
}