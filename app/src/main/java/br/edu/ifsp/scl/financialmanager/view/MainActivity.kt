package br.edu.ifsp.scl.financialmanager.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.financialmanager.R
import kotlinx.android.synthetic.main.floating_menu.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title = "Gerenciador finaceiro"
        setSupportActionBar(toolbar)


        actAccount.setOnClickListener(View.OnClickListener {
            val i = Intent(applicationContext, AccountActivity::class.java)
            startActivityForResult(i, 1)
        })

        actTransaction.setOnClickListener(View.OnClickListener {
            val i = Intent(applicationContext, TransactionActivity::class.java)
            startActivityForResult(i, 1)
        })

    }

    // Cria o menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        /*menuInflater.inflate(R.menu.main_menu, menu)*/
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       /* val i = Intent(applicationContext, AccountActivity::class.java)
        startActivityForResult(i, 1)*/
        return true;
    }



}
