package com.example.todolist.model;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import androidx.fragment.app.DialogFragment;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private final static String ARG_TIME = "arg_time";

//    private TimePickerCallback callback;

    private Calendar c;
    private long minDate;

    public static DatePickerFragment newInstance(Calendar time, long minDate) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.callback = callback;
        fragment.minDate = minDate;
        if (time != null) {
            Bundle args = new Bundle();
            args.putSerializable(ARG_TIME, time);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if (getArguments() != null && getArguments().containsKey(ARG_TIME)) {
            c = (Calendar) getArguments().getSerializable(ARG_TIME);
        }

        if (c == null) {
            c = GregorianCalendar.getInstance();
            //c.add(Calendar.DAY_OF_YEAR, 1);
        }

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
        dialog.getDatePicker().setMinDate(minDate);

        return dialog;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        if (view.isShown()) {
            if (callback != null) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, day);
                if (c.getTimeInMillis() < minDate) {
                    c = GregorianCalendar.getInstance();
                    c.add(Calendar.DAY_OF_YEAR, 1);
                }
                callback.onTimeSelected(c);
            }
        }
    }
}