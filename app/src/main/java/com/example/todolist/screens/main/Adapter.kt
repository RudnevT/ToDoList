package com.example.todolist.screens.main

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import com.example.todolist.App.Companion.instance
import com.example.todolist.R
import com.example.todolist.model.Task

class Adapter : RecyclerView.Adapter<Adapter.TaskViewHolder>() {
    private val sortedList
            : SortedList<Task>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_task_list, parent, false))
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(sortedList.get(position))
    }

    override fun getItemCount(): Int {
        return sortedList.size()
    }

    fun setItems(tasks: List<Task>?) {
        sortedList.replaceAll(tasks!!)
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var taskText: TextView
        var delete: View
        var task: Task? = null

        fun bind(task: Task) {
            this.task = task
            taskText.text = task.text
            updateCrossOut()
        }

        fun updateCrossOut(){
            if(task!!.done){
                taskText.paintFlags = taskText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                instance!!.taskDao!!.update(task)
            } else{
                taskText.paintFlags = taskText.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                instance!!.taskDao!!.update(task)
            }
        }

        init {
            taskText = itemView.findViewById(R.id.task_text)
            delete = itemView.findViewById(R.id.delete)
            itemView.setOnClickListener {
                if(!task!!.done) {
                    task!!.done = true
                    updateCrossOut()
                }else{
                    task!!.done = false
                    updateCrossOut()
                }
            }
            delete.setOnClickListener { instance!!.taskDao!!.delete(task) }
        }
    }

    init {
        sortedList = SortedList(Task::class.java, object : SortedList.Callback<Task>() {
            override fun compare(o1: Task, o2: Task): Int {
                return (o2.timeStamp - o1.timeStamp).toInt()
            }

            override fun onChanged(position: Int, count: Int) {
                notifyItemRangeChanged(position, count)
            }

            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(item1: Task, item2: Task): Boolean {
                return item1.uid == item2.uid
            }

            override fun onInserted(position: Int, count: Int) {
                notifyItemRangeInserted(position, count)
            }

            override fun onRemoved(position: Int, count: Int) {
                notifyItemRangeRemoved(position, count)
            }

            override fun onMoved(fromPosition: Int, toPosition: Int) {
                notifyItemMoved(fromPosition, toPosition)
            }
        })
    }
}