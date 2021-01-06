package com.example.todolist.screens.main

import androidx.lifecycle.ViewModel
import com.example.todolist.App.Companion.instance
import java.time.DayOfWeek
import java.util.*


class MainViewModel: ViewModel() {

    val taskLiveData = instance!!.taskDao!!.getAllTasksLiveData()
    val taskTodayLiveData = instance!!.taskDao!!.getTodayTasksLiveData(initLowerLimit(),initUpperLimit())

    fun initLowerLimit(): Long {
        val calendar: Calendar = GregorianCalendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY,0)
        calendar.set(Calendar.MINUTE,0)
        calendar.set(Calendar.SECOND,0)
        calendar.set(Calendar.MILLISECOND,0)
        return calendar.timeInMillis
    }

    fun initUpperLimit(): Long {
        val calendar: Calendar = GregorianCalendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY,0)
        calendar.set(Calendar.MINUTE,0)
        calendar.set(Calendar.SECOND,0)
        calendar.set(Calendar.MILLISECOND,0)
        calendar.add(Calendar.DAY_OF_MONTH,1)
        return calendar.timeInMillis
    }
}