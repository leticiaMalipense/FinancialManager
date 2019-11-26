import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import java.util.*


//Date Picker Fragment recendo o campo edit text ao qual ele ira apresentar a data selecionada
class DatePickeFragment(var fieldDate: View) : DialogFragment(), DatePickerDialog.OnDateSetListener {

    //Sobrescrvendo metodo onCreateDialog para criar o DatePicker com os dados defaul da data atual
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)

        val datePickerDialog = DatePickerDialog(activity!!,this, year, month, day)

        return datePickerDialog
    }

    //Metodo invocado após selecionar a data no datepicker, atribuindo a data seleciona a um field para ser exibido na tela
    override fun onDateSet(datePicker: DatePicker, year: Int, month: Int, day: Int) {
        val edtData = this.fieldDate as EditText
        edtData.setText("$day/$month/$year")
        edtData.setError(null)//removes error
        edtData.clearFocus()
    }


}