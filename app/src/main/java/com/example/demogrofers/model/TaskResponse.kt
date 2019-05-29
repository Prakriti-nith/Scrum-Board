package com.example.demogrofers.model

data class TaskResponse (
    var id: Long,
    var title: String,
    var description: String,
    var status: String,
    var createdAt: String,
    var updatedAt: String
)