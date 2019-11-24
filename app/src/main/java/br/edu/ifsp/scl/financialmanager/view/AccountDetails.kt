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

class AccountDetails : AppCompatActivity() {
    internal lateinit var account: Account

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_details)

        toolbar.title = "Gerenciando Conta"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

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
        if (id == R.id.deleteAccount) {
            val service = AccountService(this)
            service.delete(account.id)

            Toast.makeText(applicationContext, "Conta excluída com sucesso", Toast.LENGTH_LONG).show()
            setResult(RESULT_OK, Intent().putExtra(MainActivity.Constantes.ACCOUNT, account))
            finish()

        }
        finish()
        return true
    }

}
