package com.example.demogrofers.repository

import com.example.demogrofers.api.ScrumBoardApis
import com.example.demogrofers.model.Task


class ScrumBoardRepository(private val scrumBoardApis: ScrumBoardApis) {
    fun getScrumBoardRepository() = scrumBoardApis.getAllCurrentTasks()
    fun postNewTask(taskToSend: Task) = scrumBoardApis.postNewTask(taskToSend)
}