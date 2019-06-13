package com.example.financas.ui.activity

import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.AdapterView
import com.example.financas.R
import com.example.financas.database.AppDatabase
import com.example.financas.database.dao.OldTransacaoDao
import com.example.financas.delegate.TransacaoDelegate
import com.example.financas.model.Tipo
import com.example.financas.model.Transacao
import com.example.financas.ui.ResumoView
import com.example.financas.ui.adapter.ListaTransacoesAdapter
import com.example.financas.ui.dialog.AdicionaTransacaoDialog
import com.example.financas.ui.dialog.AlteraTransacaoDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

class ListaTransacoesActivity : AppCompatActivity() {

    private val database by lazy {
        Room.databaseBuilder(this, AppDatabase::class.java, "app-database").allowMainThreadQueries().build()
    }

    private val dao by lazy { database.transacaoDao() }

    //private val transacoes by lazy { dao.all() }

    //private lateinit var viewActivity: View

    private val viewActivity by lazy { window.decorView }

    private val viewGroupActivity by lazy { viewActivity as ViewGroup }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_lista_transacoes)

        //viewActivity = window.decorView

        configuraAdapter()

        configuraResumo()

        configuraFab()

    }

    fun atualizaTransacoes() {
        configuraAdapter()
        configuraResumo()
    }

    fun configuraAdapter() {
        val listaTransacoesAdapter = ListaTransacoesAdapter(this, dao.all())
        with(lista_transacoes_listview) {
            adapter = listaTransacoesAdapter
            setOnItemClickListener { parent, view, index, id ->
                val transacao = adapter.getItem(index) as Transacao
                openDialogAlterar(transacao)
            }
            setOnCreateContextMenuListener { menu, view, menuInfo ->
                menu.add(Menu.NONE, 1, Menu.NONE, "Remover")
            }
        }

    }

    fun configuraResumo() {

        val resumoView = ResumoView(this, viewActivity, dao.all())
        resumoView.atualiza()

    }

    fun configuraFab() {
        lista_transacoes_adiciona_receita.setOnClickListener { openDialogAdicionar(Tipo.RECEITA) }
        lista_transacoes_adiciona_despesa.setOnClickListener { openDialogAdicionar(Tipo.DESPESA) }
    }

    fun openDialogAdicionar(tipo: Tipo) {
        AdicionaTransacaoDialog(this, viewGroupActivity)
            .show(
                tipo,
                object : TransacaoDelegate {
                    override fun delegate(transacao: Transacao) {
                        adiciona(transacao)
                        lista_transacoes_adiciona_menu.close(true)
                    }

                    override fun cancel() {
                        lista_transacoes_adiciona_menu.close(true)
                    }
                }
            )
    }

    fun adiciona(transacao: Transacao) {
        dao.add(transacao)
        atualizaTransacoes()
    }

    fun openDialogAlterar(transacao: Transacao) {
        AlteraTransacaoDialog(this, viewGroupActivity)
            .show(transacao, object : TransacaoDelegate {
                override fun delegate(transacao: Transacao) {
                    altera(transacao)
                    lista_transacoes_adiciona_menu.close(true)
                }

                override fun cancel() {
                    lista_transacoes_adiciona_menu.close(true)
                }
            })
    }

    fun altera(transacao: Transacao) {
        dao.edit(transacao)
        atualizaTransacoes()
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        val menuId = item?.itemId
        if (menuId == 1) {
            val adapterMenuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
            val id = adapterMenuInfo.id
            remove(id)
        }
        return super.onContextItemSelected(item)
    }

    fun remove(id: Long) {
        val transacao = dao.getById(id)
        dao.remove(transacao)
        atualizaTransacoes()
    }
}
