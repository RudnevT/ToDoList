package com.example.todolist.screens.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.App.Companion.instance
import com.example.todolist.R
import com.example.todolist.components.DatePickerCallback
import com.example.todolist.components.DatePickerFragment
import java.util.*

class TasksFragment : Fragment(R.layout.fragment_content_main), DatePickerCallback {
    private lateinit var v: View
    private var recyclerView: RecyclerView? = null
//    private lateinit var menu: Menu
    private val adapter = Adapter()
    var mainViewModel: MainViewModel? = null
    object PickDate {
        val pickDate: Calendar = GregorianCalendar.getInstance()
    }

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
//        menu?.findItem(R.id.pick_date_item)?.title = String.format("%tF",selectDate)

        return v
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main,menu)
//        this.menu = menu
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.pick_date_item ->{
                val dps = DatePickerFragment.newInstance(PickDate.pickDate, System.currentTimeMillis(), this)
                val fragmentManager: FragmentManager? = childFragmentManager
                item.title = String.format("%tF",PickDate.pickDate)
                dps.show(fragmentManager!!, "tag")

                mainViewModel?.taskSelectDateLiveData1?.observe(this, { tasks ->
                    adapter.setItems(tasks) })

                Toast.makeText(requireContext(),String.format("%tF",PickDate.pickDate), LENGTH_LONG).show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}