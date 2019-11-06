package br.edu.ifsp.scl.financialmanager.view

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.financialmanager.R
import br.edu.ifsp.scl.financialmanager.controller.AccountController
import br.edu.ifsp.scl.financialmanager.model.Account
import kotlinx.android.synthetic.main.activity_account.*
import kotlinx.android.synthetic.main.toolbar.*

class AccountActivity : AppCompatActivity() {

    lateinit var accountController: AccountController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        toolbar.title = "Adicionar conta"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        accountController = AccountController(this)
        btnCreateAccount.setOnClickListener(::onClickCreateAccount)

        accountController.findAllAccount()
    }

    fun onClickCreateAccount(v: View) {

        if(validateFieldsRequeried()) {
            val description = edtDescription.text.toString();
            val value = edtValue.text.toString().toDouble();

            val account: Account = Account( 0, description, value)

            accountController.createAccount(account)

            Toast.makeText(applicationContext, "Conta incluida com sucesso", Toast.LENGTH_LONG).show()
        }

    }

    fun validateFieldsRequeried() : Boolean {
        var validated = true;
        if(edtDescription.text == null ||  edtDescription.text.isEmpty()){
            edtDescription.setError("Campo descrição é de preenchimento obrigatório")
            validated = false;
        }

        if(edtValue.text == null ||  edtValue.text.isEmpty()){
            edtValue.setError("Campo saldo inicial é de preenchimento obrigatório")
            validated =  false;
        }

        return validated;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}
