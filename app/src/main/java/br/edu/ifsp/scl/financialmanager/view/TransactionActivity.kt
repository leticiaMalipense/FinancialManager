package br.edu.ifsp.scl.financialmanager.view

import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.financialmanager.R

import kotlinx.android.synthetic.main.activity_transaction.*
import kotlinx.android.synthetic.main.toolbar.*

class TransactionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)


        toolbar.title = "Adicionar transação"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

}
