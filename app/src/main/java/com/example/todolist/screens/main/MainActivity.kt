package com.example.todolist.screens.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.widget.Button
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.components.DatePickerCallback
import com.example.todolist.components.DatePickerFragment
import com.example.todolist.screens.create.TaskDetailsFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), DatePickerCallback {

    private var recyclerView: RecyclerView? = null
    private lateinit var pagerAdapter: ViewPagerAdapter
    private var pickDateBtn: Button? = null

    companion object {
        var selectedDate: Calendar = GregorianCalendar.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))


        pagerAdapter = ViewPagerAdapter(supportFragmentManager)
        pagerAdapter.addFragment(TasksFragment(), "Все задачи")
        pagerAdapter.addFragment(TaskDetailsFragment(), "Новая задача")
        view_pager.adapter = pagerAdapter
        tabs.setupWithViewPager(view_pager)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)

        pickDateBtn = findViewById(R.id.pick_date_btn)

        val dpd = DatePickerFragment.newInstance(selectedDate, System.currentTimeMillis(), this)
        val fragmentManager: FragmentManager? = supportFragmentManager
        pickDateBtn?.setOnClickListener {
            dpd.show(fragmentManager!!, "tag")
        }
    }


//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//
//        val mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
//        val adapter = Adapter()
//        recyclerView!!.adapter = adapter
//
//        return when (item.itemId) {
//            R.id.action_settings -> true
//
//            R.id.show_today -> {
//                mainViewModel.taskTodayLiveData.observe(this, { tasks -> adapter.setItems(tasks) })
//                true
//            }
//            R.id.show_all -> {
//                mainViewModel.taskLiveData.observe(this, { tasks -> adapter.setItems(tasks) })
//                true
//            }
//
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
}