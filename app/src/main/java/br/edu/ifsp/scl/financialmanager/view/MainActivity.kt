package br.edu.ifsp.scl.financialmanager.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.financialmanager.adapter.AccountAdapter
import br.edu.ifsp.scl.financialmanager.R
import br.edu.ifsp.scl.financialmanager.controller.MainController
import br.edu.ifsp.scl.financialmanager.model.Account
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.floating_menu.*
import kotlinx.android.synthetic.main.toolbar.*


class MainActivity : AppCompatActivity() {
    lateinit var adapter: AccountAdapter
    lateinit var controller: MainController
    var currentValue = 0.0

    object Constantes{
        val ACCOUNT = "ACCOUNT"
        val ACCOUNT_REQUEST_CODE = 1
        val ACCOUNT_DETAILS_REQUEST_CODE = 2
        val TRANSACTIONS_REQUEST_CODE = 3
        val EXTRACTS_REQUEST_CODE = 4
        val EXTRACT_RESULT_REQUEST_CODE = 5
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title = "Gerenciador finaceiro"
        setSupportActionBar(toolbar)

        createEventsFloagtingMenu()

        createAccountList()

        adapter.accounts.forEach({ currentValue += it.value })
        txtCurrentBalanceValue.setText("R$: $currentValue")

    }

    override fun onResume() {
        super.onResume()
        menu.close(false)
    }

    private fun createAccountList() {
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
                startActivityForResult(i, Constantes.ACCOUNT_DETAILS_REQUEST_CODE)

            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constantes.ACCOUNT_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK){

            val account = data?.getParcelableExtra<Account>(Constantes.ACCOUNT)

            if (account != null) {
                adapter.addAccount(account)
                currentValue += account.value
                txtCurrentBalanceValue.setText("R$: $currentValue")
            }
        }
        else if (requestCode == Constantes.ACCOUNT_DETAILS_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK){

            val account = data?.getParcelableExtra<Account>(Constantes.ACCOUNT)

            if (account != null) {
                adapter.removeAccount(account)
                currentValue -= account.value
                txtCurrentBalanceValue.setText("R$: $currentValue")
            }
        }

    }


    private fun createEventsFloagtingMenu() {
        menu.hideMenuButton(false)
        Handler().postDelayed(Runnable { menu.showMenuButton(true) }, 400)
        menu.setClosedOnTouchOutside(true)

        actAccount.setOnClickListener(View.OnClickListener {
            val i = Intent(applicationContext, AccountActivity::class.java)
            startActivityForResult(i, Constantes.ACCOUNT_REQUEST_CODE)
        })

        actTransaction.setOnClickListener(View.OnClickListener {
            val i = Intent(applicationContext, TransactionActivity::class.java)
            startActivityForResult(i, Constantes.TRANSACTIONS_REQUEST_CODE)
        })

        actExtracts.setOnClickListener(View.OnClickListener {
            val i = Intent(applicationContext, ExtractsActivity::class.java)
            startActivityForResult(i, Constantes.EXTRACTS_REQUEST_CODE)
        })
    }


}
