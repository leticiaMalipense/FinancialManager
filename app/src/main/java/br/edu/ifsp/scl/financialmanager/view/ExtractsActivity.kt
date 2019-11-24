package br.edu.ifsp.scl.financialmanager.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import br.edu.ifsp.scl.financialmanager.R
import br.edu.ifsp.scl.financialmanager.service.AccountService
import br.edu.ifsp.scl.financialmanager.enums.Classification
import br.edu.ifsp.scl.financialmanager.enums.TransectionType
import kotlinx.android.synthetic.main.activity_extracts.*
import kotlinx.android.synthetic.main.toolbar.*

class ExtractsActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extracts)

        toolbar.title = "Extratos"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        rdExtractAccount.setOnCheckedChangeListener(this);
        rdExtractTransection.setOnCheckedChangeListener(this);
        rdExtractType.setOnCheckedChangeListener(this);

        var accountService = AccountService(this)

        val adapterAccounts = ArrayAdapter(this, android.R.layout.simple_spinner_item, accountService.findAll())
        spExtractAccounts.adapter = adapterAccounts

        val adapterClassification = ArrayAdapter(this, android.R.layout.simple_spinner_item, Classification.values())
        spExtractClassification.adapter = adapterClassification

        val adapterType = ArrayAdapter(this, android.R.layout.simple_spinner_item, TransectionType.values())
        spExtractType.adapter = adapterType

        btnGenerateExtract.setOnClickListener(::generateExtract)
    }

    fun generateExtract(v: View) {
        val i = Intent(applicationContext, ExtractResultsActivity::class.java)
        startActivityForResult(i, MainActivity.Constantes.EXTRACT_RESULT_REQUEST_CODE)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (isChecked) {
            if (buttonView?.id == R.id.rdExtractAccount) {
                rdExtractTransection.setChecked(false);
                rdExtractType.setChecked(false);
            }
            if (buttonView?.id == R.id.rdExtractTransection) {
                rdExtractAccount.setChecked(false);
                rdExtractType.setChecked(false);
            }
            if (buttonView?.id == R.id.rdExtractType) {
                rdExtractAccount.setChecked(false);
                rdExtractTransection.setChecked(false);
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}
