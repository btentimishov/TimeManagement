package baktiyar.com.testfragment.utils

import android.app.Dialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.widget.TextView
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import baktiyar.com.testfragment.R
import java.util.Calendar

/**
 * Created by admin on 23.04.2018.
 */
class TimePickerFragment : DialogFragment(), OnTimeSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val hour = c[Calendar.HOUR]
        val minute = c[Calendar.MINUTE]
        return TimePickerDialog(activity, this, hour, minute, true)
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        val textView = requireActivity().findViewById<TextView>(R.id.tvDoTime)
        textView.text = "$hourOfDay:$minute"
    }
}