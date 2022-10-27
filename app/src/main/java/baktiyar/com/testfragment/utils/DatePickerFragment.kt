package baktiyar.com.testfragment.utils

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import baktiyar.com.testfragment.R
import java.util.Calendar

/**
 * Created by admin on 21.04.2018.
 */
class DatePickerFragment : DialogFragment(), OnDateSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c[Calendar.YEAR]
        val month = c[Calendar.MONTH]
        val day = c[Calendar.DAY_OF_MONTH]
        return DatePickerDialog(requireActivity(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        val tvDoDate = requireActivity().findViewById<TextView>(R.id.tvDoDate)
        tvDoDate.text = view.dayOfMonth.toString() + "/" + view.month + "/" + view.year
    }
}