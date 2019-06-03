package com.example.demogrofers.model

import java.io.Serializable

data class TaskResponse (
    var id: Long,
    var title: String,
    var description: String,
    var status: String,
    var createdAt: String,
    var updatedAt: String
) : Serializable