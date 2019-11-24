package br.edu.ifsp.scl.financialmanager.view

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.financialmanager.R
import br.edu.ifsp.scl.financialmanager.enums.Classification
import br.edu.ifsp.scl.financialmanager.enums.Period
import br.edu.ifsp.scl.financialmanager.model.Account
import br.edu.ifsp.scl.financialmanager.service.AccountService
import kotlinx.android.synthetic.main.activity_account.*
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

            Toast.makeText(applicationContext, "Transação incluida com sucesso", Toast.LENGTH_LONG).show()

            finish()
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
