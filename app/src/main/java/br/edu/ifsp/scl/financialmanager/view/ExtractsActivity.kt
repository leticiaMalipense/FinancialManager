package br.edu.ifsp.scl.financialmanager.view

import DatePickeFragment
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.get
import br.edu.ifsp.scl.financialmanager.R
import br.edu.ifsp.scl.financialmanager.service.AccountService
import br.edu.ifsp.scl.financialmanager.enums.Classification
import br.edu.ifsp.scl.financialmanager.enums.TransactionType
import br.edu.ifsp.scl.financialmanager.model.Transaction
import br.edu.ifsp.scl.financialmanager.service.TransactionService
import kotlinx.android.synthetic.main.activity_extracts.*
import kotlinx.android.synthetic.main.activity_transaction.*
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

        val adapterAccounts = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, accountService.findAll())
        spExtractAccounts.adapter = adapterAccounts

        val adapterClassification = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, Classification.values())
        spExtractClassification.adapter = adapterClassification

        val adapterType = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, TransactionType.values())
        spExtractType.adapter = adapterType

        btnGenerateExtract.setOnClickListener(::generateExtract)
        toogleEnableComponents()
    }

    fun generateExtract(v: View) {
        val transactionService = TransactionService(this)
        val accountService = AccountService(this)

        var transactions = ArrayList<Transaction>()

        if (rdExtractTransection.isChecked) {
            val transactionTypeId = TransactionType.getEnumFromDescription((spExtractType.get(0) as AppCompatTextView).text.toString()).id
            transactions = transactionService.findByTransactionType(transactionTypeId) as ArrayList<Transaction>
        } else if (rdExtractType.isChecked) {
            val classificationId = Classification.getEnumFromDescription((spExtractClassification.get(0) as AppCompatTextView).text.toString()).id
            transactions = transactionService.findByTransactionClassification(classificationId) as ArrayList<Transaction>
        } else if (rdExtractAccount.isChecked) {
            val accountId = accountService.findByName((spExtractAccounts.get(0) as AppCompatTextView).text.toString()).id
            val beginDate = if (edtBeginDate.text.toString().isEmpty()) null else edtBeginDate.text.toString()
            val endDate = if (edtEndDate.text.toString().isEmpty()) null else edtEndDate.text.toString()
            transactions = transactionService.findByDate(accountId, beginDate, endDate) as ArrayList<Transaction>
        }
        if (transactions.isEmpty()) {
            Toast.makeText(this, "Nenhuma transação encontrada", Toast.LENGTH_SHORT).show()
        } else {
            val i = Intent(applicationContext, ExtractResultsActivity::class.java)
            i.putExtra(MainActivity.Constantes.TRASACTION_LIST, transactions)
            i.putExtra(MainActivity.Constantes.TYPE_TRANSACTION, (spExtractType.get(0) as AppCompatTextView).text.toString())
            startActivityForResult(i, MainActivity.Constantes.EXTRACT_RESULT_REQUEST_CODE)
        }


    }

    fun toogleEnableComponents() {
        // desabilita todos os componentes
        spExtractAccounts.isEnabled = false
        edtBeginDate.isEnabled = false
        edtEndDate.isEnabled = false
        spExtractClassification.isEnabled = false
        spExtractType.isEnabled = false

        // reabilita conforme radio selecionado
        if (rdExtractTransection.isChecked) {
            spExtractType.isEnabled = true
        } else if (rdExtractType.isChecked) {
            spExtractClassification.isEnabled = true
        } else if (rdExtractAccount.isChecked) {
            spExtractAccounts.isEnabled = true
            edtBeginDate.isEnabled = true
            edtEndDate.isEnabled = true
        }
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
            toogleEnableComponents()
        }
    }


    //Exibe campo do datePicker
    fun showPickerDialogBegin(v: View) {
        val newFragment = DatePickeFragment(edtBeginDate)
        newFragment.show(supportFragmentManager, "datePicker")
    }

    //Exibe campo do datePicker
    fun showPickerDialogEnd(v: View) {
        val newFragment = DatePickeFragment(edtEndDate)
        newFragment.show(supportFragmentManager, "datePicker")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}
