package com.example.to_dolist

data class Task(
    val id: Int,
    val description: String,
    var isCompleted: Boolean
)
