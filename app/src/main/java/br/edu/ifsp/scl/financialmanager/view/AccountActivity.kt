package br.edu.ifsp.scl.financialmanager.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.financialmanager.R
import br.edu.ifsp.scl.financialmanager.controller.AccountController
import br.edu.ifsp.scl.financialmanager.enums.Bank
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



        // Initialize a new array with elements
        val banks = ArrayList<String>()
        Bank.values().forEach({ banks.add(it.description) })

        // Initialize a new array adapter object
        val adapter = ArrayAdapter<String>(
            this, // Context
            android.R.layout.simple_dropdown_item_1line, // Layout
            banks // Array
        )

        // Set the AutoCompleteTextView adapter
        edtDescription.setAdapter(adapter)

        // Auto complete threshold
        // The minimum number of characters to type to show the drop down
        edtDescription.threshold = 1

        // Set an item click listener for auto complete text view
        edtDescription.onItemClickListener = AdapterView.OnItemClickListener{ parent, view, position, id->
            val selectedItem = parent.getItemAtPosition(position).toString()
            // Display the clicked item using toast
            Toast.makeText(applicationContext,"Selected : $selectedItem",Toast.LENGTH_SHORT).show()
        }

        // Set a focus change listener for auto complete text view
        edtDescription.onFocusChangeListener = View.OnFocusChangeListener{
                view, b ->
            if(b){
                // Display the suggestion dropdown on focus
                edtDescription.showDropDown()
            }
        }

    }

    fun onClickCreateAccount(v: View) {
        //Valida campos
        if(validateFieldsRequeried()) {

            //Recupera valores da tela
            val description = edtDescription.text.toString()
            val value = edtValue.text.toString().toDouble()

            if(controller.findByName(description).id <= 0) {

                //Cria objeto account com os valores informados nos campos da tela
                val account: Account = Account( 0, description, value)

                //Chama metodos para criação da conta na base
                controller.createAccount(account)

                //Atribue account criada ao map extra para que seja possivel recupera-la após a transição para outra activity
                setResult(RESULT_OK, Intent().putExtra(MainActivity.Constantes.ACCOUNT, account))

                //Cria Toast para apresentar mensagem de criação com sucesso
                Toast.makeText(applicationContext, "Conta incluida com sucesso", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(applicationContext, "Conta já existente", Toast.LENGTH_LONG).show()
            }
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
