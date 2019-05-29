package com.example.demogrofers.model

import java.io.Serializable

data class Task (
    var title: String,
    var description: String,
    var status: String
) : Serializable