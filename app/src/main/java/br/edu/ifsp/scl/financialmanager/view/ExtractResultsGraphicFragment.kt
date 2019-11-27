package br.edu.ifsp.scl.financialmanager.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.edu.ifsp.scl.financialmanager.R
import br.edu.ifsp.scl.financialmanager.model.Transaction
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.fragment_extract_results_graphic.view.*
import java.util.*


//Fragment que exibe o resultado da pesquisa em forma de grafico
class ExtractResultsGraphicFragment(var transactions: List<Transaction>): Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_extract_results_graphic, container, false)

        val NoOfEmp = ArrayList<Entry>()

        NoOfEmp.add(Entry(945f, 0))
        NoOfEmp.add(Entry(1040f, 1))
        NoOfEmp.add(Entry(1133f, 2))
        NoOfEmp.add(Entry(1240f, 3))
        NoOfEmp.add(Entry(1369f, 4))
        NoOfEmp.add(Entry(1487f, 5))
        NoOfEmp.add(Entry(1501f, 6))
        NoOfEmp.add(Entry(1645f, 7))
        NoOfEmp.add(Entry(1578f, 8))
        NoOfEmp.add(Entry(1695f, 9))
        val dataSet = PieDataSet(NoOfEmp, "Number Of Employees")

        val year = ArrayList<String>()

        year.add("2008")
        year.add("2009")
        year.add("2010")
        year.add("2011")
        year.add("2012")
        year.add("2013")
        year.add("2014")
        year.add("2015")
        year.add("2016")
        year.add("2017")
        val data = PieData(year, dataSet)
        view.piechart.setData(data)
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS)
        view.piechart.animateXY(5000, 5000)

        return view

    }


}