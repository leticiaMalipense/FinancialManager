package br.edu.ifsp.scl.financialmanager.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.financialmanager.R
import br.edu.ifsp.scl.financialmanager.controller.TransactionController
import br.edu.ifsp.scl.financialmanager.enums.Classification
import br.edu.ifsp.scl.financialmanager.enums.Period
import br.edu.ifsp.scl.financialmanager.enums.TransactionType
import br.edu.ifsp.scl.financialmanager.model.Account
import br.edu.ifsp.scl.financialmanager.model.Transaction
import br.edu.ifsp.scl.financialmanager.service.AccountService
import kotlinx.android.synthetic.main.activity_transaction.*
import kotlinx.android.synthetic.main.toolbar.*


class TransactionActivity : AppCompatActivity() {

    lateinit var controller: TransactionController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)


        toolbar.title = "Adicionar transação"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        controller = TransactionController(this)
        btnCreateTransaction.setOnClickListener(::onClickCreateTransaction)

        fillSpinners()

    }

    fun onClickCreateTransaction(v: View) {

        if(validateFieldsRequeried()) {

            val description = edtDescTransaction.text.toString()
            val value = edtValueTransaction.text.toString().toDouble()
            val account = spAccounts.getSelectedItem() as Account
            val classification = spClassification.getSelectedItem() as Classification
            val period = spPeriod.getSelectedItem() as Period
            val type = if (rdCredit.isChecked) TransactionType.CREDITO else TransactionType.DEBITO

            val transaction: Transaction = Transaction( 0, description, value, account.id, type.id, classification.id, period.id)

            controller.createTransaction(transaction)

            setResult(RESULT_OK, Intent().putExtra(MainActivity.Constantes.ACCOUNT_ID, account.id))
            Toast.makeText(applicationContext, "Transação incluida com sucesso", Toast.LENGTH_LONG).show()
            finish()
        }

    }

    fun validateFieldsRequeried() : Boolean {
        var validated = true;

        /*if(spAccounts.selectedItemId <= 0){
            var errorText = spAccounts.getSelectedView() as TextView
            errorText.setError("Campo descrição é de preenchimento obrigatório")
            validated = false;
        }*/

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

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

}
