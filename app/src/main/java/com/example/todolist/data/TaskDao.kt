package com.example.todolist.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todolist.model.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM Task")
    fun getTasks(): List<Task>

    @Query("SELECT * FROM Task")
    fun getAllTasksLiveData(): LiveData<List<Task>>

    @Query("SELECT * FROM Task WHERE uid IN (:tasksIds)")
    fun loadAllTasksByIds(tasksIds: Array<Int>): List<Task>

    @Query("SELECT * FROM Task WHERE scheduledDate LIKE :date")
    fun getTodayTasksLiveData(date: Long): LiveData<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(task: Task?)

    @Update
    fun update(task: Task?)

    @Delete
    fun delete(task: Task?)
}