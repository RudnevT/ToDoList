package com.example.todolist.screens.main

import androidx.lifecycle.ViewModel
import com.example.todolist.App.Companion.instance
import java.util.*


class MainViewModel: ViewModel() {

    val taskLiveData = instance!!.taskDao!!.getAllTasksLiveData()
    val taskTodayLiveData = instance!!.taskDao!!.getTodayTasksLiveData(initCurrentDate())

    fun initCurrentDate(): Long {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY,0)
        calendar.set(Calendar.MINUTE,0)
        calendar.set(Calendar.SECOND,0)
        calendar.set(Calendar.MILLISECOND,0)
//        var cal: Long = calendar.getTimeInMillis()
        return calendar.getTimeInMillis()
    }
}