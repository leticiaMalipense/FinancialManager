package br.edu.ifsp.scl.financialmanager.view

import android.os.Bundle
import android.view.MenuItem
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.financialmanager.R
import br.edu.ifsp.scl.financialmanager.model.Transaction
import kotlinx.android.synthetic.main.activity_extract_results.*
import kotlinx.android.synthetic.main.toolbar.*


class ExtractResultsActivity : AppCompatActivity() {

    internal lateinit var transactions: List<Transaction>
    internal lateinit var typeTransaction: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extract_results)

        toolbar.title = "Extratos"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        if (intent.hasExtra(MainActivity.Constantes.TRASACTION_LIST)) {
            this.transactions  = intent.getParcelableArrayListExtra(MainActivity.Constantes.TRASACTION_LIST)
        }
        if (intent.hasExtra(MainActivity.Constantes.TYPE_TRANSACTION)) {
            this.typeTransaction  = intent.getStringExtra(MainActivity.Constantes.TYPE_TRANSACTION)
        }

        supportFragmentManager.beginTransaction().replace(R.id.resultFragment,
                               ExtractResultsListFragment(this.transactions)).commit()

        //Evento do switch para ativar modo grafico
        swit.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
           if(isChecked){
               supportFragmentManager.beginTransaction().replace(R.id.resultFragment,
                   ExtractResultsGraphicFragment(this.transactions, this.typeTransaction) ).commit()
           }else{
               supportFragmentManager.beginTransaction().replace(R.id.resultFragment,
                                      ExtractResultsListFragment(this.transactions)).commit()
           }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}
