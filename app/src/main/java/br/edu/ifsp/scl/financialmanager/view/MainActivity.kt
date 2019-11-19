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
import br.edu.ifsp.scl.financialmanager.controller.MainController
import br.edu.ifsp.scl.financialmanager.model.Account
import kotlinx.android.synthetic.main.floating_menu.*
import kotlinx.android.synthetic.main.toolbar.*


class MainActivity : AppCompatActivity() {
    lateinit var adapter: AccountAdapter
    lateinit var controller: MainController

    object Constantes{
        val ACCOUNT = "ACCOUNT"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title = "Gerenciador finaceiro"
        setSupportActionBar(toolbar)


        createEventsFloagtingMenu()


        val recyclerView = findViewById<RecyclerView>(R.id.accountsView)
        val layout = LinearLayoutManager(this)
        recyclerView.setLayoutManager(layout)

        controller = MainController(this)
        adapter = AccountAdapter(controller.findAllAccount())

        recyclerView.setAdapter(adapter)

        AccountAdapter.setClickListener(adapter, object : AccountAdapter.ItemClickListener {
            override fun onItemClick(position: Int) {
                val account = adapter.accounts.get(position)

                val i = Intent(applicationContext, AccountDetails::class.java)
                i.putExtra(Constantes.ACCOUNT, account)
                startActivityForResult(i, 2)

            }
        })


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK){

            val account = data?.getParcelableExtra<Account>(Constantes.ACCOUNT)

            if (account != null) {
                adapter.refreshAdapter(account)
            }
        }

    }


    private fun createEventsFloagtingMenu() {
        actAccount.setOnClickListener(View.OnClickListener {
            val i = Intent(applicationContext, AccountActivity::class.java)
            startActivityForResult(i, 1)
        })

       /* actTransaction.setOnClickListener(View.OnClickListener {
            val i = Intent(applicationContext, TransactionActivity::class.java)
            startActivityForResult(i, 1)
        })*/
    }


}
