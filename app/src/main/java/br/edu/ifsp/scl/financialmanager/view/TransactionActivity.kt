package br.edu.ifsp.scl.financialmanager.view

import DatePickeFragment
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.get
import br.edu.ifsp.scl.financialmanager.R
import br.edu.ifsp.scl.financialmanager.controller.TransactionController
import br.edu.ifsp.scl.financialmanager.enums.Classification
import br.edu.ifsp.scl.financialmanager.enums.Period
import br.edu.ifsp.scl.financialmanager.enums.TransactionType
import br.edu.ifsp.scl.financialmanager.model.Account
import br.edu.ifsp.scl.financialmanager.model.Transaction
import br.edu.ifsp.scl.financialmanager.service.AccountService
import br.edu.ifsp.scl.financialmanager.service.TransactionService
import br.edu.ifsp.scl.financialmanager.utils.MoneyMask
import kotlinx.android.synthetic.main.activity_account.*
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

        //Cria controller de transaction
        controller = TransactionController(this)

        //Adiciona evento de click ao botão salvar
        btnCreateTransaction.setOnClickListener(::onClickCreateTransaction)

        //Preencher todos os campos spinners
        fillSpinners()

        edtValueTransaction.addTextChangedListener(MoneyMask.monetario(edtValueTransaction))

    }

    fun onClickCreateTransaction(v: View) {

        if(validateFieldsRequeried()) {
            val account = spAccounts.getSelectedItem() as Account

            var transactionService = TransactionService(this)
            var accountService = AccountService(this)

            if (account != null) {
                val description = edtDescTransaction.text.toString()
                val value = MoneyMask.unmask(edtValueTransaction).toDouble()
                val accountId: Int = account.id
                val classificationId = Classification.getEnumFromDescription((spClassification.get(0) as AppCompatTextView).text.toString()).id
                val periodId = Period.getEnumFromDescription((spPeriod.get(0) as AppCompatTextView).text.toString()).id
                var transactionDate = edtTransactionDate.text.toString()
                var type = if (rdCredit.isChecked) TransactionType.CREDITO.id else TransactionType.DEBITO.id

                //Criando e inserindo a transação
                var tran = Transaction(0, description, value, accountId, transactionDate, type, classificationId, periodId)
                transactionService.create(tran)

                //atualizando o saldo da conta
                if (type == TransactionType.CREDITO.id) {
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

    //Valida campos obrigdatorios
    fun validateFieldsRequeried() : Boolean {
        var validated = true;

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

    //Exibe campo do datePicker
    fun showTimePickerDialog(v: View) {
        val newFragment = DatePickeFragment(edtTransactionDate)
        newFragment.show(supportFragmentManager, "datePicker")
    }


    private fun fillSpinners() {

        //Criada adapter de classification com base na Enum Classification
        val adapterTypes =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, Classification.values())
        spClassification.adapter = adapterTypes

        //Cria adapter de periods com base na Enum Period
        val adapterPeriods =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, Period.values())
        spPeriod.adapter = adapterPeriods

        //Cria adapter de accounts realizando consultando na base por accounts cadastradas
        var accountService = AccountService(this)
        val adapterAccounts =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, accountService.findAll())
        spAccounts.adapter = adapterAccounts
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

}
