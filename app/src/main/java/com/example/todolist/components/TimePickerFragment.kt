package com.example.todolist.components

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePickerFragment: DialogFragment(), TimePickerDialog.OnTimeSetListener {
    private var callback: TimePickerCallback? = null
    private var c: Calendar? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (arguments != null && requireArguments().containsKey(ARG_TIME)) {
            c = requireArguments().getSerializable(ARG_TIME) as Calendar?
        }
        if (c == null) {
            c = GregorianCalendar.getInstance()
        }
        val hour = c!![Calendar.HOUR_OF_DAY]
        val minute = c!![Calendar.MINUTE]
        val dialog = TimePickerDialog(requireActivity(), this, hour, minute,true)
        return dialog
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        if (view.isShown) {
            if (callback != null) {
                c!![Calendar.HOUR_OF_DAY] = hourOfDay
                c!![Calendar.MINUTE] = minute
                callback?.onTimeSelected(c)
            }
        }
    }

    companion object {
        private const val ARG_TIME = "arg_time"
        fun newInstance(
            time: Calendar?,
            callback: TimePickerCallback
        ): TimePickerFragment {
            val fragment = TimePickerFragment()
            fragment.callback = callback;
            if (time != null) {
                val args = Bundle()
                args.putSerializable(ARG_TIME, time)
                fragment.arguments = args
            }
            return fragment
        }
    }
}