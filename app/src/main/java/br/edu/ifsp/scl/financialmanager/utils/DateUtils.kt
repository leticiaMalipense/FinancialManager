package br.edu.ifsp.scl.financialmanager.utils

import java.text.SimpleDateFormat

class DateUtils {

    companion object {
        val sdfPtbr = SimpleDateFormat("dd/MM/yyyy")
        val sdfEn = SimpleDateFormat("yyyy-MM-dd")

        fun getDatePtbrFromEn(englishDateString: String):String {
            val data = sdfEn.parse(englishDateString)
            return sdfPtbr.format(data)
        }

        fun getDateEnFromPtbr(portugueseDateString: String):String {
            val data = sdfPtbr.parse(portugueseDateString)
            return sdfEn.format(data)
        }
    }

}