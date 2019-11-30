package br.edu.ifsp.scl.financialmanager.view

import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.financialmanager.adapter.AccountAdapter
import br.edu.ifsp.scl.financialmanager.R
import br.edu.ifsp.scl.financialmanager.controller.MainController
import br.edu.ifsp.scl.financialmanager.model.Account
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.floating_menu.*
import kotlinx.android.synthetic.main.toolbar.*
import java.text.NumberFormat


class MainActivity : AppCompatActivity() {
    lateinit var adapter: AccountAdapter
    lateinit var controller: MainController
    var currentValue = 0.0

    //Criando constrantes para os codigos de retorno das activity
    object Constantes{
        val ACCOUNT = "ACCOUNT"
        val TRASACTION_LIST = "TRANSACTION_LIST"
        val TYPE_TRANSACTION = "TYPE_TRANSACTION"
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
        txtCurrentBalanceValue.setText(NumberFormat.getCurrencyInstance().format(currentValue))

    }

    //Sempre que invocado o onResume deve fechar as opções do menu
    override fun onResume() {
        super.onResume()
        menu.close(false)
    }

    fun createAccountList() {
        val recyclerView = findViewById<RecyclerView>(R.id.accountsView)
        val layout = LinearLayoutManager(this)
        recyclerView.setLayoutManager(layout)

        val simpleCallback = createSimpleCallback()
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        controller = MainController(this)

        //Cria o adapter com a lista de accounts cadastradas
        adapter = AccountAdapter(controller.findAllAccount())

        recyclerView.setAdapter(adapter)
    }

    //Criar evento de desligar e excluir o item da lista
    fun createSimpleCallback() : ItemTouchHelper.SimpleCallback{
        return object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val account = adapter.accounts.get(viewHolder.adapterPosition)

                adapter.accounts.remove(account)
                adapter.notifyDataSetChanged()

                controller.delete(account.id)

                // atualiza o saldo total
                currentValue = 0.0
                adapter.accounts.forEach({ currentValue += it.value })
                txtCurrentBalanceValue.setText(NumberFormat.getCurrencyInstance().format(currentValue))

                Toast.makeText(applicationContext, "Conta excluida", Toast.LENGTH_LONG).show()

            }

            override fun onChildDraw(
                c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                val icon: Bitmap
                val p = Paint()

                val itemView = viewHolder.itemView
                val height = itemView.bottom.toFloat() - itemView.top.toFloat()
                val width = height / 3

                p.color = ContextCompat.getColor(baseContext, R.color.greyValue)

                val background = RectF(itemView.left.toFloat(), itemView.top.toFloat(), dX, itemView.bottom.toFloat())

                c.drawRect(background, p)

                icon = BitmapFactory.decodeResource(resources, android.R.drawable.ic_delete)

                val icon_dest = RectF(itemView.left.toFloat() + width, itemView.top.toFloat() + width,
                                itemView.left.toFloat() + 2 * width, itemView.bottom.toFloat() - width)

                c.drawBitmap(icon, null, icon_dest, null)

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive
                )
            }


        }

    }

    //Trata retorno de outras activities para a Main
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //Atualiza saldo atual total
        currentValue = controller.getCurrentBalance()
        txtCurrentBalanceValue.setText(NumberFormat.getCurrencyInstance().format(currentValue))

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
