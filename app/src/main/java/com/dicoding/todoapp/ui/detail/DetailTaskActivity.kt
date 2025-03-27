package com.dicoding.todoapp.ui.detail

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.todoapp.R
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.ui.list.TaskActivity
import com.dicoding.todoapp.utils.DateConverter
import com.dicoding.todoapp.utils.TASK_ID

class DetailTaskActivity : AppCompatActivity() {

    private lateinit var editTitle : EditText
    private lateinit var editDesc : EditText
    private lateinit var editDueDate : EditText
    private lateinit var delButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        var viewModelFactory : ViewModelFactory = ViewModelFactory.getInstance(this)
        var detailTaskViewModel: DetailTaskViewModel = ViewModelProvider(this,viewModelFactory)[DetailTaskViewModel::class.java]
        editTitle = findViewById(R.id.detail_ed_title)
        editDesc = findViewById(R.id.detail_ed_description)
        editDueDate = findViewById(R.id.detail_ed_due_date)
        delButton = findViewById(R.id.btn_delete_task)
        detailTaskViewModel.setTaskId(intent.getIntExtra(TASK_ID,0))
        detailTaskViewModel.task.observe(this@DetailTaskActivity){
            if (it != null){
                editTitle.setText(it.title)
                editDesc.setText(it.description)
                editDueDate.setText(DateConverter.convertMillisToString(it.dueDateMillis))

            }
            delButton.setOnClickListener {
                detailTaskViewModel.deleteTask()
                val intent = Intent(this,TaskActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }
}
