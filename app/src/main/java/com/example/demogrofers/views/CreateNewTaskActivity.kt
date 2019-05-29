package com.example.demogrofers.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.demogrofers.R

class CreateNewTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_task)

        initialize()
        setListeners()
    }

    private fun initialize() {
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setListeners() {

    }
}
