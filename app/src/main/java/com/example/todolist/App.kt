package com.example.todolist

import android.app.Application
import androidx.room.Room
import com.example.todolist.data.AppDatabase
import com.example.todolist.data.TaskDao


class App : Application() {
    var database: AppDatabase? = null
    var taskDao: TaskDao? = null
    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java,
                "task-db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        taskDao = database!!.taskDao()
    }

    companion object {
        @JvmStatic
        var instance: App? = null
            private set
    }
}