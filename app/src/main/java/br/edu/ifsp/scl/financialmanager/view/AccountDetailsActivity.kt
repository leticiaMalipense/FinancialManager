package br.edu.ifsp.scl.financialmanager.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.financialmanager.R
import br.edu.ifsp.scl.financialmanager.service.AccountService
import br.edu.ifsp.scl.financialmanager.model.Account
import kotlinx.android.synthetic.main.activity_account_details.*
import kotlinx.android.synthetic.main.toolbar.*

class AccountDetailsActivity : AppCompatActivity() {
    internal lateinit var account: Account

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_details)

        //Atribuindo titulo a toolbar
        toolbar.title = "Gerenciando Conta"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //Recuperar valores do map extra que foram enviado pela activity anterior
        if (intent.hasExtra(MainActivity.Constantes.ACCOUNT)) {
            this.account  = intent.getParcelableExtra<Account>(MainActivity.Constantes.ACCOUNT)

            val description = txtDetailsDescription
            description.setText(account.description)

            val value = txtDetailsValue
            value.setText(account.value.toString())

        }
    }

    // Cria o menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.account_details_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        //Opção de delete do menu
        if (id == R.id.deleteAccount) {

            //Instancia accountService
            val service = AccountService(this)
            service.delete(account.id)

            //Cria toast com a mensagem de exclusão com sucesso
            Toast.makeText(applicationContext, "Conta excluída com sucesso", Toast.LENGTH_LONG).show()

            //Seta account excluida para a proxima tela para que a lista de accounts possa ser atualizada
            setResult(RESULT_OK, Intent().putExtra(MainActivity.Constantes.ACCOUNT, account))

            finish()

        }
        finish()
        return true
    }

}
