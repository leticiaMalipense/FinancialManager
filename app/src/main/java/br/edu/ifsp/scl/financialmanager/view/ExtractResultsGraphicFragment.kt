package br.edu.ifsp.scl.financialmanager.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.edu.ifsp.scl.financialmanager.R
import br.edu.ifsp.scl.financialmanager.enums.Classification
import br.edu.ifsp.scl.financialmanager.model.Transaction
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.fragment_extract_results_graphic.view.*
import java.util.*
import kotlin.collections.HashMap


//Fragment que exibe o resultado da pesquisa em forma de grafico
class ExtractResultsGraphicFragment(var transactions: List<Transaction>, var typeTransaction: String): Fragment(){

    val yvalues = ArrayList<Entry>()
    val xVals = ArrayList<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_extract_results_graphic, container, false)

        view.pieChart.setUsePercentValues(true)

        val map = HashMap<Int, Float>()

        //Agrupar as transações por classificação, assim é possivel saber quanto(%) foi gasto em cada categoria
        agroupTransactionsByClassification(map)

        map.keys.forEach{ xVals.add(Classification.getEnumFromId(it).description) }

        //Utilizar o mapa dos valores de transações agrupados, para montar os objetos do grafico
        map.entries.forEach{ yvalues.add(Entry(it.value, it.key)) }

        val dataSet = PieDataSet(yvalues, "")
        val data = PieData(xVals, dataSet)
        data.setValueFormatter(PercentFormatter())
        view.pieChart.setData(data)
        view.pieChart.setDescription("Gastos por natureza de transação")

        view.pieChart.setRotationAngle(0f)
        view.pieChart.setRotationEnabled(true)

        view.pieChart.setDrawHoleEnabled(true)
        view.pieChart.setTransparentCircleRadius(25f)
        view.pieChart.setHoleRadius(25f)
        view.pieChart.centerText = typeTransaction

        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS)
        data.setValueTextSize(13f)
        data.setValueTextColor(Color.DKGRAY)

        view.pieChart.animateXY(1400, 1400)

        return view

    }

    //Metodo pra transformar a lista de transações em um map de gasto X classificação
    private fun agroupTransactionsByClassification(map: HashMap<Int, Float>) {
        transactions.forEach {
            if (map.get(it.classificationId) == null) {
                map.put(it.classificationId, it.value.toFloat())
            } else {
                var currentValue = map.get(it.classificationId) as Float
                map.put(it.classificationId, it.value.toFloat() + currentValue)
            }

        }
    }


}