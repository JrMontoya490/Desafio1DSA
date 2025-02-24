package com.example.to_dolist

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: TaskDatabaseHelper
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var taskList: MutableList<Task>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = TaskDatabaseHelper(this)
        val taskEditText: EditText = findViewById(R.id.task_edit_text)
        val addButton: Button = findViewById(R.id.add_button)
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(this)
        taskList = dbHelper.getTasks().toMutableList()
        taskAdapter = TaskAdapter(taskList, this::updateTaskStatus, this::deleteTask)
        recyclerView.adapter = taskAdapter

        addButton.setOnClickListener {
            val taskText = taskEditText.text.toString()
            if (taskText.isNotEmpty()) {
                dbHelper.addTask(taskText)
                updateTaskList()
                taskEditText.text.clear()
            }
        }
    }

    private fun updateTaskStatus(task: Task) {
        dbHelper.updateTaskStatus(task.id, task.isCompleted)
        updateTaskList()
    }

    private fun deleteTask(taskId: Int) {
        dbHelper.deleteTask(taskId)
        updateTaskList()
    }

    private fun updateTaskList() {
        taskList.clear()
        taskList.addAll(dbHelper.getTasks())
        taskAdapter.notifyDataSetChanged()
    }
}
