package br.edu.ifsp.scl.financialmanager.view

import android.content.Intent
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

    lateinit var controller: AccountController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        //Atribuindo titulo a toolbar
        toolbar.title = "Adicionar conta"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //Instancia a controller
        controller = AccountController(this)

        //Seta envoto de click ao botão de salvar
        btnCreateAccount.setOnClickListener(::onClickCreateAccount)

    }

    fun onClickCreateAccount(v: View) {
        //Valida campos
        if(validateFieldsRequeried()) {

            //Recupera valores da tela
            val description = edtDescription.text.toString()
            val value = edtValue.text.toString().toDouble()

            //Cria objeto account com os valores informados nos campos da tela
            val account: Account = Account( 0, description, value)

            //Chama metodos para criação da conta na base
            controller.createAccount(account)

            //Atribue account criada ao map extra para que seja possivel recupera-la após a transição para outra activity
            setResult(RESULT_OK, Intent().putExtra(MainActivity.Constantes.ACCOUNT, account))

            //Cria Toast para apresentar mensagem de criação com sucesso
            Toast.makeText(applicationContext, "Conta incluida com sucesso", Toast.LENGTH_LONG).show()
            finish()
        }

    }

    //Função para validar campos obrigatórios
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

    //A toolbar possui apenas o evento de back, finalizando a atual activity e voltando para a ultima da pilha
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}
