package com.example.todolist.components

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.text.SimpleDateFormat
import java.util.*

class DatePickerFragment : DialogFragment(), OnDateSetListener {
    private var callback: DatePickerCallback? = null
    private var c: Calendar? = null
    private var minDate: Long = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (arguments != null && requireArguments().containsKey(ARG_TIME)) {
            c = requireArguments().getSerializable(ARG_TIME) as Calendar?
        }
        if (c == null) {
            c = GregorianCalendar.getInstance()
        }
        val year = c!![Calendar.YEAR]
        val month = c!![Calendar.MONTH]
        val day = c!![Calendar.DAY_OF_MONTH]
        val dialog = DatePickerDialog(requireActivity(), this, year, month, day)
        dialog.datePicker.minDate = minDate
        return dialog
    }


    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        if (view.isShown) {
            if (callback != null) {
                c!![Calendar.YEAR] = year
                c!![Calendar.MONTH] = month
                c!![Calendar.DAY_OF_MONTH] = day
                c!![Calendar.HOUR_OF_DAY] = 0
                c!![Calendar.MINUTE] = 0
                c!![Calendar.SECOND] = 0
                c!![Calendar.MILLISECOND] = 0
                callback?.onDateSelected(c)
            }
        }
    }

    companion object {
        private const val ARG_TIME = "arg_time"
        fun newInstance(
            time: Calendar?,
            minDate: Long,
            callback: DatePickerCallback
        ): DatePickerFragment {
            val fragment = DatePickerFragment()
            fragment.callback = callback;
            fragment.minDate = minDate
            if (time != null) {
                val args = Bundle()
                args.putSerializable(ARG_TIME, time)
                fragment.arguments = args
            }
            return fragment
        }
    }
}