package com.example.todolist.screens.create

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.todolist.App.Companion.instance
import com.example.todolist.R
import com.example.todolist.model.Task
import kotlinx.android.synthetic.main.fragment_task_details.*
import java.util.*

class TaskDetailsActivity : AppCompatActivity()
    , DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener
{
    var task: Task? = null
    private var editText: EditText? = null
    private var dateDisplay: TextView? = null
    private var timeDisplay: TextView? = null

    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMinute = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_task_details)

        picDate()
        picTime()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)

        title = getString(R.string.note_details_title)
        editText = findViewById(R.id.text)
        dateDisplay = findViewById(R.id.btn_date_display)
        timeDisplay = findViewById(R.id.btn_time_display)

        if (intent.hasExtra(EXTRA_TASK)) {
            task = intent.getParcelableExtra(EXTRA_TASK)
            editText!!.setText(task!!.text)
//            dateDisplay!!.setText(task!!.scheduledDate)
//            timeDisplay!!.setText(task!!.scheduledTime)
        } else {
            task = Task()
        }
    }

    private fun getDateCalendar(){
        val cal: Calendar = Calendar.getInstance()
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)
    }

    private fun getTimeCalendar(){
        val cal: Calendar = Calendar.getInstance()
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)
    }


    private fun picDate(){
        btn_date_display.setOnClickListener{
            getDateCalendar()
            DatePickerDialog(this,this, year,month,day).show()
        }
    }

    private fun picTime(){
        btn_time_display.setOnClickListener{
            getTimeCalendar()
            TimePickerDialog(this,this, hour, minute,true).show()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month + 1  //this is a fix bug
        savedYear = year

        btn_date_display.text = "$savedDay.$savedMonth.$savedYear"
    }

    @SuppressLint("SetTextI18n")
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute

        btn_time_display.text = "$savedHour:$savedMinute"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_details, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.action_save -> if (editText!!.text.isNotEmpty()) {
                task!!.text = editText!!.text.toString()
                task!!.timeStamp = System.currentTimeMillis()
//                task!!.scheduledDate = dateDisplay!!.text.toString()
//                //для теста
//                Toast.makeText(this,dateDisplay!!.text.toString(), LENGTH_LONG).show()
//                val sdf = SimpleDateFormat("dd.MM.yyyy")
//                var date = sdf.format(Date())
//                Toast.makeText(this,date, LENGTH_LONG).show()
//                //конец теста
//                task!!.scheduledTime = timeDisplay!!.text.toString()
                if (intent.hasExtra(EXTRA_TASK)) {
                    instance!!.taskDao!!.update(task)
                } else {
                    instance!!.taskDao!!.insert(task)
                }
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val EXTRA_TASK = "TaskDetailsActivity.EXTRA_TASK"
        fun start(caller: Activity, task: Task?) {
            val intent = Intent(caller, TaskDetailsActivity::class.java)
            if (task != null) {
                intent.putExtra(EXTRA_TASK, task)
            }
            caller.startActivity(intent)
        }
    }
}