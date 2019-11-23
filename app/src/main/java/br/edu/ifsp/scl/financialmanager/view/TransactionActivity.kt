package br.edu.ifsp.scl.financialmanager.view

import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.financialmanager.R
import br.edu.ifsp.scl.financialmanager.enums.Classification
import br.edu.ifsp.scl.financialmanager.enums.Period
import kotlinx.android.synthetic.main.activity_transaction.*
import kotlinx.android.synthetic.main.toolbar.*


class TransactionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)


        toolbar.title = "Adicionar transação"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        val adapterType = ArrayAdapter(this, android.R.layout.simple_spinner_item, Classification.values())
        spClassification.adapter = adapterType

        val adapterPeriod = ArrayAdapter(this, android.R.layout.simple_spinner_item, Period.values())
        spPeriod.adapter = adapterPeriod

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

}
