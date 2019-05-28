package com.example.demogrofers.interfaces

import com.example.demogrofers.model.Task

interface ListListener{
    fun onItemClick(task: Task)
}