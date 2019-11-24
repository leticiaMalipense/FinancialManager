package br.edu.ifsp.scl.financialmanager.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.scl.financialmanager.Adapter.ExtractAdapter
import br.edu.ifsp.scl.financialmanager.R
import kotlinx.android.synthetic.main.activity_extract_results.*
import kotlinx.android.synthetic.main.toolbar.*

class ExtractResultsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extract_results)

        toolbar.title = "Extratos"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val layoutManager = LinearLayoutManager(this)
        rvExtract.setLayoutManager(layoutManager)
        val adapter = ExtractAdapter()
        rvExtract.setAdapter(adapter)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}
