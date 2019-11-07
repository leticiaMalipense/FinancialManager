package br.edu.ifsp.scl.financialmanager.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.financialmanager.Adapter.AccountAdapter
import br.edu.ifsp.scl.financialmanager.R
import br.edu.ifsp.scl.financialmanager.Service.AccountService
import br.edu.ifsp.scl.financialmanager.controller.AccountController
import br.edu.ifsp.scl.financialmanager.model.Account
import kotlinx.android.synthetic.main.floating_menu.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {
    lateinit var adapter: AccountAdapter
    lateinit var controller: AccountController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title = "Gerenciador finaceiro"
        setSupportActionBar(toolbar)


        createEventsFloagtingMenu()


        val recyclerView = findViewById<RecyclerView>(R.id.accountsView)
        val layout = LinearLayoutManager(this)
        recyclerView.setLayoutManager(layout)

        controller = AccountController(this)
        adapter = AccountAdapter(controller.findAllAccount())

        recyclerView.setAdapter(adapter)

        /* adapter.setClickListener(object : AccountAdapter.ItemClickListener() {
            fun onItemClick(position: Int) {
                val c = adapter.getContactListFiltered().get(position)

                val i = Intent(applicationContext, DetalheActivity::class.java)
                i.putExtra("contato", c)
                startActivityForResult(i, 2)

            }
        })*/


    }

    fun refreshListAccount(account: Account) = {
        adapter.addAccountAdapter(account)
    }

    private fun createEventsFloagtingMenu() {
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
