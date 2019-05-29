package com.example.demogrofers.views

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import com.example.demogrofers.R
import com.example.demogrofers.databinding.ActivityCreateNewTaskBinding
import com.example.demogrofers.model.Task
import com.example.demogrofers.utils.FilterStates

class CreateNewTaskActivity : AppCompatActivity() {

    private lateinit var activityCreateNewTaskBinding: ActivityCreateNewTaskBinding

    companion object {
        const val SPINNER_DEFAULT_VALUE = "Select the status of task"
        const val HASH_MAP_POST_REQUEST = "HashMap"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityCreateNewTaskBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_new_task)

        initialize()
        setListeners()
    }

    private fun initialize() {
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setListeners() {
        activityCreateNewTaskBinding.saveBtn.setOnClickListener {
            sendDataBack(it)
        }
    }

    private fun sendDataBack(view: View) {
        val title = activityCreateNewTaskBinding.titleEt.text.toString()
        val description = activityCreateNewTaskBinding.descriptionEt.text.toString()
        val status = activityCreateNewTaskBinding.statusSpinner.selectedItem.toString()
        if(title.isNullOrEmpty() || description.isNullOrEmpty() || status == SPINNER_DEFAULT_VALUE) {
            Snackbar.make(view, "Complete all the fields provided", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        else {

            var taskToPost = Task(title, description, status)

            val intent = Intent(this@CreateNewTaskActivity, ScrumBoardMainActivity::class.java)
            intent.putExtra(HASH_MAP_POST_REQUEST, taskToPost)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
