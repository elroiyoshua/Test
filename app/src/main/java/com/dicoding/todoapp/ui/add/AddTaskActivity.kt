package com.dicoding.todoapp.ui.add

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dicoding.todoapp.R
import com.dicoding.todoapp.data.Task
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.ui.detail.DetailTaskViewModel
import com.dicoding.todoapp.ui.list.TaskActivity
import com.dicoding.todoapp.utils.DatePickerFragment
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class AddTaskActivity : AppCompatActivity(), DatePickerFragment.DialogDateListener {
    private var dueDateMillis: Long = System.currentTimeMillis()
    private lateinit var addTitle : EditText
    private lateinit var addDesc : EditText



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        supportActionBar?.title = getString(R.string.add_task)
        addTitle = findViewById(R.id.add_ed_title)
        addDesc = findViewById(R.id.add_ed_description)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {

                val viewModelFactory : ViewModelFactory = ViewModelFactory.getInstance(this)
                val addTaskViewModel: AddTaskViewModel = ViewModelProvider(this,viewModelFactory)[AddTaskViewModel::class.java]
                val judul = addTitle.text.toString()
                val deskripsi = addDesc.text.toString()
                if (judul.isNotEmpty() && deskripsi.isNotEmpty()){
                    val newTask = Task(0,judul,deskripsi,dueDateMillis)
                    addTaskViewModel.apply {
                        insertTask(newTask)
                    }
                    Toast.makeText(this@AddTaskActivity, "Add Task Succes", Toast.LENGTH_SHORT).show()
                    backToTask()
                }else{
                    Toast.makeText(this@AddTaskActivity, "Please fill all the column", Toast.LENGTH_SHORT).show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showDatePicker(view: View) {
        val dialogFragment = DatePickerFragment()
        dialogFragment.show(supportFragmentManager, "datePicker")
    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        findViewById<TextView>(R.id.add_tv_due_date).text = dateFormat.format(calendar.time)

        dueDateMillis = calendar.timeInMillis
    }
    private fun backToTask(){
        val intent = Intent(this,TaskActivity::class.java)
        startActivity(intent)
        finish()
    }
}
