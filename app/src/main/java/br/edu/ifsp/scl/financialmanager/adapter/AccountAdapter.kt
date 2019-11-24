package br.edu.ifsp.scl.financialmanager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.financialmanager.R
import br.edu.ifsp.scl.financialmanager.model.Account

class AccountAdapter(accounts: List<Account>) : RecyclerView.Adapter<AccountAdapter.AccountViewHolder>() {
    var accounts: MutableList<Account>
    lateinit var context: Context
    lateinit var clickListener: ItemClickListener

    init {
        this.accounts = mutableListOf<Account>()
        this.accounts.addAll(accounts)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.account_cell, parent, false)

        this.context = parent.context
        return AccountViewHolder(view)
    }

    override fun getItemCount(): Int {
        return accounts.size
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        holder.description.setText(accounts.get(position).description)
        holder.value.setText(accounts.get(position).value.toString())
    }


    inner class AccountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        internal val description: TextView
        internal val value: TextView

        init {
            description = itemView.findViewById(R.id.txtDescription) as TextView
            value = itemView.findViewById(R.id.txtValue) as TextView
            itemView.setOnClickListener(this)

        }

        override fun onClick(v: View) {
            clickListener.onItemClick(adapterPosition)
        }
    }

    fun addAccount(account: Account) {
        this.accounts.add(account)
        notifyDataSetChanged()
    }

    fun removeAccount(account: Account) {
        this.accounts.remove(account)
        notifyDataSetChanged()
    }

    fun updateAccount(account: Account) {

    }


    interface ItemClickListener {
        fun onItemClick(position: Int)
    }

    companion object {
        fun setClickListener(accountAdapter: AccountAdapter, itemClickListener: ItemClickListener) {
            accountAdapter.clickListener = itemClickListener
        }
    }


}