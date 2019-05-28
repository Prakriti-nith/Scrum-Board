package com.example.demogrofers.repository

import com.example.demogrofers.api.ScrumBoardApis


class ScrumBoardRepository(private val scrumBoardApis: ScrumBoardApis) {
    fun getScrumBoardRepository() = scrumBoardApis.getAllCurrentTasks()
}