package br.edu.ifsp.scl.financialmanager.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.financialmanager.R
import br.edu.ifsp.scl.financialmanager.adapter.ExtractAdapter
import br.edu.ifsp.scl.financialmanager.model.Transaction
import kotlinx.android.synthetic.main.fragment_extract_results_list.*
import kotlinx.android.synthetic.main.fragment_extract_results_list.view.*

//Fragment que exibe o resultado da pesquisa em forma de lista
class ExtractResultsListFragment(var transactions: List<Transaction>): Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_extract_results_list, container, false)

        val layoutManager = LinearLayoutManager(context)
        view.rvExtract.setLayoutManager(layoutManager)

        val adapter = ExtractAdapter(this.transactions)
        view.rvExtract.setAdapter(adapter)

        return view

    }

}