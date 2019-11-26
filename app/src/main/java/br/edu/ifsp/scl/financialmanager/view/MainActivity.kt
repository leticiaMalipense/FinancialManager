package br.edu.ifsp.scl.financialmanager.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
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

    //Criando constrantes para os codigos de retorno das activity
    object Constantes{
        val ACCOUNT = "ACCOUNT"
        val TRASACTION_LIST = "TRANSACTION_LIST"
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

        //Criand reciclerView com a lista de accounts cadastradas
        createAccountList()

        //Adicionando menu flutuante
        createEventsFloagtingMenu()

        adapter.accounts.forEach({ currentValue += it.value })
        txtCurrentBalanceValue.setText("R$: $currentValue")

    }

    //Sempre que invocado o onResume deve fechar as opções do menu
    override fun onResume() {
        super.onResume()
        menu.close(false)
    }

    private fun createAccountList() {
        val recyclerView = findViewById<RecyclerView>(R.id.accountsView)
        val layout = LinearLayoutManager(this)
        recyclerView.setLayoutManager(layout)

        controller = MainController(this)

        //Cria o adapter com a lista de accounts cadastradas
        adapter = AccountAdapter(controller.findAllAccount())

        recyclerView.setAdapter(adapter)

        //Adiciona evento de click nos itens da lista, para pagina de detalhes
        AccountAdapter.setClickListener(adapter, object : AccountAdapter.ItemClickListener {
            override fun onItemClick(position: Int) {
                val account = adapter.accounts.get(position)

                val i = Intent(applicationContext, AccountDetailsActivity::class.java)
                i.putExtra(Constantes.ACCOUNT, account)
                startActivityForResult(i, Constantes.ACCOUNT_DETAILS_REQUEST_CODE)

            }
        })
    }

    //Trata retorno de outras activities para a Main
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //Atualiza saldo atual total
        currentValue = controller.getCurrentBalance()
        txtCurrentBalanceValue.setText("R$: $currentValue")

        //Trata retorno ok da AccountActivity
        if (requestCode == Constantes.ACCOUNT_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK){
            val account = data?.getParcelableExtra<Account>(Constantes.ACCOUNT)

            if (account != null) {
                adapter.accounts.add(account)
                adapter.notifyDataSetChanged()
            }
        }

        //Trata retorno ok da AccountDetailsActivity
        else if (requestCode == Constantes.ACCOUNT_DETAILS_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK){
            val account = data?.getParcelableExtra<Account>(Constantes.ACCOUNT)

            if (account != null) {
                adapter.accounts.remove(account)
                adapter.notifyDataSetChanged()
            }
        }

        //Trata retorno ok da TransactionActivity
        else if (requestCode == Constantes.TRANSACTIONS_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {

            val account = data?.getParcelableExtra<Account>(Constantes.ACCOUNT)

            if (account != null) {
                var index = -1
                for ((i, a) in  adapter.accounts.withIndex()) {
                   if (account.id == a.id) {
                       index = i
                   }
                }
                if (index != -1) {
                    adapter.accounts[index] = account
                }
                adapter.notifyItemChanged(index)
            }
        }

    }

    //Criando evento de click nas opçoes do menu
    private fun createEventsFloagtingMenu() {
        menu.hideMenuButton(false)

        //Animação para colocar o menu na tela
        Handler().postDelayed(Runnable { menu.showMenuButton(true) }, 400)

        //Bloquear click para areas foras das opções do menu
        menu.setClosedOnTouchOutside(true)

        //Evento para opção de criar conta do menu
        actAccount.setOnClickListener(View.OnClickListener {
            val i = Intent(applicationContext, AccountActivity::class.java)
            startActivityForResult(i, Constantes.ACCOUNT_REQUEST_CODE)
        })

        //Ao selecionar a opção de transação deve existir ao menos uma conta criada
        actTransaction.setOnClickListener(View.OnClickListener {

            //Valida se existe conta
            if (adapter.accounts.isEmpty()) {
                Toast.makeText(this,"Adicione uma conta primeiro", Toast.LENGTH_SHORT).show();
            } else {

                //Redicionar para o activity de transação caso passe na validação
                val i = Intent(applicationContext, TransactionActivity::class.java)
                startActivityForResult(i, Constantes.TRANSACTIONS_REQUEST_CODE)
            }
        })

        //Ao selecionar a opção de extrato deve existir ao menos uma conta criada
        actExtracts.setOnClickListener(View.OnClickListener {
            if (adapter.accounts.isEmpty()) {
                Toast.makeText(this,"Adicione uma conta primeiro", Toast.LENGTH_SHORT).show();
            } else {

                //Exibe activity de extrato
                val i = Intent(applicationContext, ExtractsActivity::class.java)
                startActivityForResult(i, Constantes.EXTRACTS_REQUEST_CODE)
            }
        })
    }


}
