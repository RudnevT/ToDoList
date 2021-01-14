package com.example.todolist.screens.main

import androidx.lifecycle.ViewModel
import com.example.todolist.App.Companion.instance
import java.util.*


class MainViewModel: ViewModel() {
    val date: Calendar = TasksFragment.PickDate.pickDate
    val taskLiveData = instance!!.taskDao!!.getAllTasksLiveData()
    val taskTodayLiveData = instance!!.taskDao!!.getTodayTasksLiveData(initLowerLimit(TasksFragment.PickDate.pickDate),initUpperLimit(
        TasksFragment.PickDate.pickDate))
    val taskSelectDateLiveData = instance!!.taskDao!!.getSelectDateTasksLiveData(initLowerLimit(
        TasksFragment.PickDate.pickDate),initUpperLimit(TasksFragment.PickDate.pickDate))
    val taskSelectDateLiveData1 = instance!!.taskDao!!.getSelectDateTasksLiveData1(TasksFragment.PickDate.pickDate.timeInMillis)

    fun initLowerLimit(c: Calendar): Long {
        val calendar: Calendar = c
        calendar.set(Calendar.HOUR_OF_DAY,0)
        calendar.set(Calendar.MINUTE,0)
        calendar.set(Calendar.SECOND,0)
        calendar.set(Calendar.MILLISECOND,0)
        return calendar.timeInMillis
    }

    fun initUpperLimit(c: Calendar): Long {
        val calendar: Calendar = c
        calendar.set(Calendar.HOUR_OF_DAY,0)
        calendar.set(Calendar.MINUTE,0)
        calendar.set(Calendar.SECOND,0)
        calendar.set(Calendar.MILLISECOND,0)
        calendar.add(Calendar.DAY_OF_MONTH,1)
        return calendar.timeInMillis
    }
}