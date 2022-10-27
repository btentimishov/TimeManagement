package baktiyar.com.testfragment.utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

import baktiyar.com.testfragment.R;

/**
 * Created by admin on 21.04.2018.
 */

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        TextView tvDoDate = getActivity().findViewById(R.id.tvDoDate);
        tvDoDate.setText(view.getDayOfMonth() + "/" + view.getMonth() + "/" + view.getYear());
    }


}