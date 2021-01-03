package com.example.todolist.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R

class TasksFragment : Fragment(R.layout.fragment_content_main) {
    private lateinit var v: View
    private var recyclerView: RecyclerView? = null
    private val adapter = Adapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_content_main, container, false)
        recyclerView = v.findViewById(R.id.list)
        recyclerView!!.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView!!.adapter = adapter
        return v
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mainViewModel.taskTodayLiveData.observe(this, { tasks ->
            adapter.setItems(tasks)
        })

    }

}