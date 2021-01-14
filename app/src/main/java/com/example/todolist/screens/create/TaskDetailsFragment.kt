package com.example.todolist.screens.create

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.todolist.App.Companion.instance
import com.example.todolist.R
import com.example.todolist.components.DatePickerCallback
import com.example.todolist.components.DatePickerFragment
import com.example.todolist.components.TimePickerCallback
import com.example.todolist.components.TimePickerFragment
import com.example.todolist.model.Task
import kotlinx.android.synthetic.main.fragment_task_details.*
import kotlinx.android.synthetic.main.fragment_task_details.view.*
import java.text.SimpleDateFormat
import java.util.*

class TaskDetailsFragment : Fragment(R.layout.fragment_task_details), DatePickerCallback, TimePickerCallback {
    lateinit var v: View
    var task: Task? = null
    var editText: EditText? = null
    var dateDisplay: TextView? = null
    var timeDisplay: TextView? = null
    private val selectDate: Calendar = GregorianCalendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_task_details, container, false)

        editText = v.findViewById(R.id.text)
        dateDisplay = v.findViewById(R.id.btn_date_display)
        timeDisplay = v.findViewById(R.id.btn_time_display)


        v.button_save.setOnClickListener {
            saveTask()
        }

        val dps = DatePickerFragment.newInstance(selectDate, System.currentTimeMillis(), this)
        val tps = TimePickerFragment.newInstance(selectDate,this)
        val fragmentManager: FragmentManager? = childFragmentManager

        v.btn_date_display.text = String.format("%tF",selectDate)
        v.btn_time_display.text = String.format("%tR",selectDate)

        v.btn_date_display.setOnClickListener {
            dps.show(fragmentManager!!, "tag")
        }

        v.btn_time_display.setOnClickListener {
            tps.show(fragmentManager!!,"tag")
        }

        return v
    }

    override fun onDateSelected(c: Calendar?) {
        super.onDateSelected(c)
        btn_date_display.text = String.format("%tF",c)
    }

    override fun onTimeSelected(c: Calendar?) {
        super.onTimeSelected(c)
        btn_time_display!!.text = String.format("%tR",c)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    fun timeRound(_calendar: Calendar): Long? {
        val calendar = _calendar
        val roundHalf: Int = 30
        val roundFull: Int = 0
        if (calendar.get(Calendar.MINUTE) in 1..30) {
            calendar.set(Calendar.MINUTE, roundHalf)
        } else if (calendar.get(Calendar.MINUTE) in 31..60)
            calendar.set(Calendar.MINUTE, roundFull)
        return calendar.timeInMillis
    }

    private fun saveTask() {
        task = Task()
        if (editText!!.text.isNotEmpty()) {
            task!!.text = editText!!.text.toString()
            task!!.timeStamp = System.currentTimeMillis()
            task!!.scheduledDate = selectDate.timeInMillis
//            task!!.scheduledTime = timeRound(initScheduledTime())
        }
//        Toast.makeText(requireContext(), task!!.scheduledDate.toString(), LENGTH_LONG).show()
//        Toast.makeText(requireContext(), task!!.scheduledTime.toString(), LENGTH_LONG).show()

        instance!!.taskDao!!.insert(task)
        editText!!.text = null

    }

}
