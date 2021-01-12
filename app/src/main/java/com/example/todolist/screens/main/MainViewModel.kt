package com.example.todolist.screens.main

import androidx.lifecycle.ViewModel
import com.example.todolist.App.Companion.instance
import java.time.DayOfWeek
import java.util.*


class MainViewModel: ViewModel() {
    val date: Calendar = MainActivity.selectedDate
    val taskLiveData = instance!!.taskDao!!.getAllTasksLiveData()
    val taskTodayLiveData = instance!!.taskDao!!.getTodayTasksLiveData(initLowerLimit(),initUpperLimit())
    val taskSelectDateLiveData = instance!!.taskDao!!.getSelectDateTasksLiveData(initLowerLimit(),initUpperLimit())

    fun initLowerLimit(): Long {
//        val calendar: Calendar = GregorianCalendar.getInstance()
        date.set(Calendar.HOUR_OF_DAY,0)
        date.set(Calendar.MINUTE,0)
        date.set(Calendar.SECOND,0)
        date.set(Calendar.MILLISECOND,0)
        return date.timeInMillis
    }

    fun initUpperLimit(): Long {
//        val calendar: Calendar = GregorianCalendar.getInstance()
        date.set(Calendar.HOUR_OF_DAY,0)
        date.set(Calendar.MINUTE,0)
        date.set(Calendar.SECOND,0)
        date.set(Calendar.MILLISECOND,0)
        date.add(Calendar.DAY_OF_MONTH,1)
        return date.timeInMillis
    }
}