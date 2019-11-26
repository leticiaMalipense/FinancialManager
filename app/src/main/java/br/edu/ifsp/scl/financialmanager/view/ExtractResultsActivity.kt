package br.edu.ifsp.scl.financialmanager.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.scl.financialmanager.adapter.ExtractAdapter
import br.edu.ifsp.scl.financialmanager.R
import br.edu.ifsp.scl.financialmanager.model.Account
import br.edu.ifsp.scl.financialmanager.model.Transaction
import kotlinx.android.synthetic.main.activity_account_details.*
import kotlinx.android.synthetic.main.activity_extract_results.*
import kotlinx.android.synthetic.main.toolbar.*

class ExtractResultsActivity : AppCompatActivity() {

    internal lateinit var transactions: List<Transaction>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extract_results)

        toolbar.title = "Extratos"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val layoutManager = LinearLayoutManager(this)
        rvExtract.setLayoutManager(layoutManager)

        if (intent.hasExtra(MainActivity.Constantes.TRASACTION_LIST)) {
            this.transactions  = intent.getParcelableArrayListExtra(MainActivity.Constantes.TRASACTION_LIST)
        }

        val adapter = ExtractAdapter(this.transactions)
        rvExtract.setAdapter(adapter)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}
