import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.LightingColorFilter
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import androidx.appcompat.resources.R

import androidx.fragment.app.DialogFragment
import java.util.*


//Date Picker Fragment recendo o campo edit text ao qual ele ira apresentar a data selecionada
class DatePickeFragment(var fieldDate: View) : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)

        val datePickerDialog = DatePickerDialog(activity!!,this, year, month, day)

        return datePickerDialog
    }


    override fun onDateSet(datePicker: DatePicker, year: Int, month: Int, day: Int) {
        val edtData = this.fieldDate as EditText
        edtData.setText("$day/$month/$year")
        edtData.setError(null)//removes error
        edtData.clearFocus()
    }


}