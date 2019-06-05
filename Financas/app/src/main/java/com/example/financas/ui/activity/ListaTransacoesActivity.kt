package com.example.financas.ui.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.AdapterView
import com.example.financas.R
import com.example.financas.delegate.TransacaoDelegate
import com.example.financas.extension.format
import com.example.financas.model.Tipo
import com.example.financas.model.Transacao
import com.example.financas.ui.adapter.ListaTransacoesAdapter
import com.example.financas.ui.dialog.AdicionaTransacaoDialog
import com.example.financas.ui.dialog.AlteraTransacaoDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import kotlinx.android.synthetic.main.resumo_card.*
import java.math.BigDecimal

class ListaTransacoesActivity : AppCompatActivity() {

    private val transacoes: MutableList<Transacao> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_lista_transacoes)

        initAdapter()

        totalizar()

        configuraMenu()

    }

    fun atualizaTransacoes() {
        initAdapter()
        totalizar()
    }

    fun initAdapter() {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(this, transacoes)
        lista_transacoes_listview.setOnItemClickListener { parent, view, index, id ->
            val transacao = transacoes[index]
            openDialogAlterar(transacao, index)
        }
        lista_transacoes_listview.setOnCreateContextMenuListener { menu, view, menuInfo ->
            menu.add(Menu.NONE, 1, Menu.NONE, "Remover")
        }
    }

    fun totalizar() {

        var totalReceita = BigDecimal.ZERO
        var totalDespesa = BigDecimal.ZERO

        resumo_card_receita.setTextColor(ContextCompat.getColor(this, R.color.receita))
        resumo_card_despesa.setTextColor(ContextCompat.getColor(this, R.color.despesa))

        for (transacao in transacoes) {

            if (transacao.tipo == Tipo.RECEITA)
                totalReceita = totalReceita.plus(transacao.valor)

            if (transacao.tipo == Tipo.DESPESA)
                totalDespesa = totalDespesa.plus(transacao.valor)
        }

        val total = totalReceita.minus(totalDespesa);

        resumo_card_receita.text = totalReceita.format()
        resumo_card_despesa.text = totalDespesa.format()
        resumo_card_total.text = total.format()

        resumo_card_total.setTextColor(ContextCompat.getColor(this, R.color.receita))
        if (total <= BigDecimal.ZERO) {
            resumo_card_total.setTextColor(ContextCompat.getColor(this, R.color.despesa))
        }

    }

    fun configuraMenu() {
        lista_transacoes_adiciona_receita.setOnClickListener { openDialogAdicionar(Tipo.RECEITA) }
        lista_transacoes_adiciona_despesa.setOnClickListener { openDialogAdicionar(Tipo.DESPESA) }
    }

    fun openDialogAdicionar(tipo: Tipo) {
        AdicionaTransacaoDialog(this, window.decorView as ViewGroup)
            .show(
                tipo,
                object : TransacaoDelegate {
                    override fun delegate(transacao: Transacao) {
                        transacoes.add(transacao)
                        atualizaTransacoes()
                        lista_transacoes_adiciona_menu.close(true)
                    }

                    override fun cancel() {
                        lista_transacoes_adiciona_menu.close(true)
                    }
                }
            )
    }

    fun openDialogAlterar(transacao: Transacao, index: Int) {
        AlteraTransacaoDialog(this, window.decorView as ViewGroup)
            .show(transacao, object : TransacaoDelegate {
                override fun delegate(transacao: Transacao) {
                    transacoes[index] = transacao
                    atualizaTransacoes()
                    lista_transacoes_adiciona_menu.close(true)
                }

                override fun cancel() {
                    lista_transacoes_adiciona_menu.close(true)
                }
            })
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        val menuId = item?.itemId
        if (menuId == 1) {
            val adapterMenuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
            val index = adapterMenuInfo.position
            transacoes.removeAt(index)
            atualizaTransacoes()
        }
        return super.onContextItemSelected(item)
    }
}
