package br.edu.ifsp.scl.financialmanager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.financialmanager.R
import br.edu.ifsp.scl.financialmanager.enums.Classification
import br.edu.ifsp.scl.financialmanager.enums.TransactionType
import br.edu.ifsp.scl.financialmanager.model.Account
import br.edu.ifsp.scl.financialmanager.model.Transaction

class ExtractAdapter(transactions: List<Transaction>):RecyclerView.Adapter<ExtractAdapter.ExtractViewHolder>(){
    var transactions: MutableList<Transaction>
    lateinit var context: Context

    init {
        this.transactions = mutableListOf<Transaction>()
        this.transactions.addAll(transactions)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExtractViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_extract, parent, false)
        this.context = parent.context
        return ExtractViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExtractViewHolder, position: Int) {
        val transaction = this.transactions[position]
        val transactionType = TransactionType.getEnumFromId(transaction.typeTransaction)
        val classif = Classification.getEnumFromId(transaction.classificationId)

        holder.txtTrasactionDescription.setText(transaction.description)
        holder.txtAccountDescription.setText(transaction.accountId.toString())
        holder.txtClassification.setText(classif.description)
        holder.txtTypeTransaction.setText(transactionType.description)
        holder.txtValue.setText("- R$ " + transaction.value.toString())

        holder.txtTypeTransaction.setBackgroundResource(if (transactionType == TransactionType.CREDITO) R.drawable.rounded_corner_green else R.drawable.rounded_corner_red)
        holder.txtValue.setTextColor(ContextCompat.getColor(context, if (transactionType == TransactionType.CREDITO) R.color.greenValue else R.color.redValue))
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    inner class ExtractViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val txtTypeTransaction: TextView
        internal val txtValue: TextView
        internal val txtTrasactionDescription: TextView
        internal val txtDate: TextView
        internal val txtClassification: TextView
        internal val txtAccountDescription: TextView

        init {
            txtAccountDescription = itemView.findViewById(R.id.txtCellExtractAccountDescription) as TextView
            txtClassification = itemView.findViewById(R.id.txtCellExtractClassification) as TextView
            txtDate = itemView.findViewById(R.id.txtCellExtractDate) as TextView
            txtTrasactionDescription = itemView.findViewById(R.id.txtCellExtractTrasactionDescription) as TextView
            txtTypeTransaction = itemView.findViewById(R.id.txtCellExtractTypeTransaction) as TextView
            txtValue = itemView.findViewById(R.id.txtCellExtractValue) as TextView
        }

    }

}