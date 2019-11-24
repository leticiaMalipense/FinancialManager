package br.edu.ifsp.scl.financialmanager.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.get
import br.edu.ifsp.scl.financialmanager.R
import br.edu.ifsp.scl.financialmanager.controller.AccountController
import br.edu.ifsp.scl.financialmanager.enums.Classification
import br.edu.ifsp.scl.financialmanager.enums.Period
import br.edu.ifsp.scl.financialmanager.enums.TransactionType
import br.edu.ifsp.scl.financialmanager.model.Account
import br.edu.ifsp.scl.financialmanager.model.Transaction
import br.edu.ifsp.scl.financialmanager.service.AccountService
import br.edu.ifsp.scl.financialmanager.service.TransactionService
import kotlinx.android.synthetic.main.activity_account.*
import kotlinx.android.synthetic.main.activity_extracts.view.*
import kotlinx.android.synthetic.main.activity_transaction.*
import kotlinx.android.synthetic.main.toolbar.*


class TransactionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)


        toolbar.title = "Adicionar transação"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        btnCreateTransaction.setOnClickListener(::onClickCreateTransaction)

        fillSpinners()

    }

    fun onClickCreateTransaction(v: View) {

        if(validateFieldsRequeried()) {
            var transactionService = TransactionService(this)
            var accountService = AccountService(this)

            var account = accountService.findByDescription((spAccounts.get(0) as AppCompatTextView).text.toString())

            if (account != null) {
                val description = edtDescTransaction.text.toString();
                val value = edtValueTransaction.text.toString().toDouble();
                val accountId: Int = account.id
                val classificationId = Classification.getEnumFromDescription((spClassification.get(0) as AppCompatTextView).text.toString()).id
                val periodId = Period.getEnumFromDescription((spPeriod.get(0) as AppCompatTextView).text.toString()).id
                var transactionDate = edtTransactionDate.text.toString()
                var typeTransaction = if (rdCredit.isChecked) TransactionType.CREDITO.id else TransactionType.DEBITO.id

                //Criando e inserindo a transação
                var tran = Transaction(0,description, value, accountId, classificationId, periodId, transactionDate, typeTransaction)
                transactionService.create(tran)

                //atualizando o saldo da conta
                if (typeTransaction == TransactionType.CREDITO.id) {
                    account.value += value
                } else {
                    account.value -= value
                }
                accountService.update(account)
                setResult(AppCompatActivity.RESULT_OK, Intent().putExtra(MainActivity.Constantes.ACCOUNT, account))

                Toast.makeText(applicationContext, "Transação incluida com sucesso", Toast.LENGTH_LONG).show()

                finish()
            } else {
                Toast.makeText(applicationContext, "A conta selecionada não foi encontrada!", Toast.LENGTH_LONG).show()
            }
        }

    }

    fun validateFieldsRequeried() : Boolean {
        var validated = true;

        if(rdType.checkedRadioButtonId <= 0){
            validated = false;
        }

        if(edtDescTransaction.text == null ||  edtDescTransaction.text.isEmpty()){
            edtDescTransaction.setError("Campo descrição é de preenchimento obrigatório")
            validated = false;
        }

        if(edtValueTransaction.text == null ||  edtValueTransaction.text.isEmpty()){
            edtValueTransaction.setError("Campo saldo inicial é de preenchimento obrigatório")
            validated =  false;
        }

        return validated;
    }


    private fun fillSpinners() {
        val adapterTypes =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, Classification.values())
        spClassification.adapter = adapterTypes

        val adapterPeriods =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, Period.values())
        spPeriod.adapter = adapterPeriods

        var accountService = AccountService(this)
        val adapterAccounts =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, accountService.findAll())
        spAccounts.adapter = adapterAccounts
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {

            val checked = view.isChecked


            when (view.getId()) {
                R.id.rdCredit ->
                    if (checked) {

                    }
                R.id.rdDebit ->
                    if (checked) {

                    }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

}
