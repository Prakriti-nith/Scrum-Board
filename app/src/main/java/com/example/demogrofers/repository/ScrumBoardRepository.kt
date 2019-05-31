package com.example.demogrofers.repository

import com.example.demogrofers.api.ScrumBoardApis
import com.example.demogrofers.model.Task
import com.example.demogrofers.model.TaskToSearch
import javax.inject.Inject


class ScrumBoardRepository @Inject constructor(val scrumBoardApis: ScrumBoardApis) {
    fun getScrumBoardRepository() = scrumBoardApis.getAllCurrentTasks()
    fun postNewTask(taskToSend: Task) = scrumBoardApis.postNewTask(taskToSend)
    fun postTaskToSearch(taskToSearch: TaskToSearch) = scrumBoardApis.postTaskToSearch(taskToSearch)
}
