package com.example.financas.ui.dialog

import android.app.DatePickerDialog
import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.financas.R
import com.example.financas.delegate.TransacaoDelegate
import com.example.financas.extension.format
import com.example.financas.extension.toCalendar
import com.example.financas.model.Tipo
import com.example.financas.model.Transacao
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*

class AdicionaTransacaoDialog(
    private val context: Context,
    private val viewGroup: ViewGroup
) {

    private val viewCriada: View = criaLayout()
    private val campoData = viewCriada.form_transacao_data
    private val campoValor = viewCriada.form_transacao_valor
    private val campoCategoria = viewCriada.form_transacao_categoria

    fun show(tipo: Tipo, transacaoDelegate: TransacaoDelegate) {

        configuraCampoData()
        configuraCampoCategoria(tipo)
        configuraFormulario(tipo, transacaoDelegate)

    }

    private fun criaLayout(): View {
        return LayoutInflater.from(context).inflate(R.layout.form_transacao, viewGroup, false)
    }

    private fun configuraCampoData() {

        val hoje = Calendar.getInstance()
        val ano = hoje.get(Calendar.YEAR)
        val mes = hoje.get(Calendar.MONTH)
        val dia = hoje.get(Calendar.DAY_OF_MONTH)

        campoData.setText(hoje.format())
        campoData.setOnClickListener {
            DatePickerDialog(context,
                DatePickerDialog.OnDateSetListener { _, ano, mes, dia ->
                    val dataSelecionada = Calendar.getInstance()
                    dataSelecionada.set(ano, mes, dia)
                    campoData.setText(dataSelecionada.format())
                }
                , ano, mes, dia)
                .show()
        }
    }

    private fun configuraCampoCategoria(tipo: Tipo) {

        val categorias = categoriasPor(tipo)

        val adapter = ArrayAdapter.createFromResource(
            context,
            categorias,
            android.R.layout.simple_spinner_dropdown_item
        )

        campoCategoria.adapter = adapter

    }

    private fun categoriasPor(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA)
            return R.array.categorias_de_receita
        return R.array.categorias_de_despesa
    }

    private fun configuraFormulario(tipo: Tipo, transacaoDelegate: TransacaoDelegate) {

        val titulo = tituloPor(tipo)

        AlertDialog.Builder(context)
            .setTitle(titulo)
            .setView(viewCriada)
            .setCancelable(false)
            .setPositiveButton("Adicionar") { _, _ ->
                val txtValor = campoValor.text.toString()
                val txtData = campoData.text.toString()
                val txtCategoria = campoCategoria.selectedItem.toString()

                val valor = converteCampoValor(txtValor)

                val data = txtData.toCalendar();

                val novaTransacao = Transacao(
                    tipo = tipo,
                    valor = valor,
                    data = data,
                    categoria = txtCategoria
                )

                transacaoDelegate.delegate(novaTransacao)
            }
            .setNegativeButton("Cancelar") { _, _ -> transacaoDelegate.cancel() }
            .show()
    }

    private fun tituloPor(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA)
            return R.string.adiciona_receita
        return R.string.adiciona_despesa
    }

    private fun converteCampoValor(txtValor: String): BigDecimal {
        return try {
            BigDecimal(txtValor)
        } catch (exception: NumberFormatException) {
            Toast.makeText(context, "Falha na conversão de valor", Toast.LENGTH_LONG).show()
            BigDecimal.ZERO
        }
    }
}