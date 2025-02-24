package com.example.to_dolist

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private val tasks: MutableList<Task>,
    private val onTaskChecked: (Task) -> Unit,
    private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val taskCheckBox: CheckBox = view.findViewById(R.id.task_checkbox)
        val taskTextView: TextView = view.findViewById(R.id.task_text)
        val deleteButton: Button = view.findViewById(R.id.delete_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]

        holder.taskTextView.text = task.description
        holder.taskCheckBox.setOnCheckedChangeListener(null)
        holder.taskCheckBox.isChecked = task.isCompleted

        // cambio de color según estado
        if (task.isCompleted) {
            holder.taskTextView.setTextColor(Color.GRAY)
        } else {
            holder.taskTextView.setTextColor(Color.BLACK)
        }

        //Volver a asignar el listener después de modificar el estado
        holder.taskCheckBox.setOnCheckedChangeListener { _, isChecked ->
            task.isCompleted = isChecked
            onTaskChecked(task)

            //cambio de color dinámicamente
            holder.taskTextView.setTextColor(if (isChecked) Color.MAGENTA else Color.BLACK)
        }

        //botón eliminar
        holder.deleteButton.setOnClickListener {
            onDelete(task.id)
        }
    }



    override fun getItemCount() = tasks.size
}
