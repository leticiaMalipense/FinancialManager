package br.edu.ifsp.scl.financialmanager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.financialmanager.R

class ExtractAdapter():RecyclerView.Adapter<ExtractAdapter.ExtractViewHolder>(){

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExtractViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_extract, parent, false)
        this.context = parent.context
        return ExtractViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExtractViewHolder, position: Int) {
//        val contato = contactListFiltered[position]
        if(position == 1){
            holder.txtTypeTransaction.setText("Crédito")
            holder.txtTypeTransaction.setBackgroundResource(R.drawable.rounded_corner_green)
            holder.txtValue.setTextColor(ContextCompat.getColor(context, R.color.greenValue))
        }
//        holder.imgFavoritar.setImageResource(if (contato.getFavorito()) android.R.drawable.btn_star_big_on else android.R.drawable.btn_star_big_off)
    }

    override fun getItemCount(): Int {
//        return contactListFiltered.size
        return 2
    }

    inner class ExtractViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val txtTypeTransaction: TextView
        internal val txtValue: TextView


        init {
            txtTypeTransaction = itemView.findViewById(R.id.txtCellExtractTypeTransaction) as TextView
            txtValue = itemView.findViewById(R.id.txtCellExtractValue) as TextView
        }

    }

}