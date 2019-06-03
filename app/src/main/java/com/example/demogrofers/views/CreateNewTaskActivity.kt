package com.example.demogrofers.views

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import com.example.demogrofers.R
import com.example.demogrofers.databinding.ActivityCreateNewTaskBinding
import com.example.demogrofers.model.Task
import android.view.MenuItem
import com.example.demogrofers.ConnectionUtils
import com.example.demogrofers.model.TaskResponse
import com.example.demogrofers.viewmodel.CreateNewTaskViewModel
import com.example.demogrofers.viewmodel.ViewModelFactory
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import android.widget.ArrayAdapter



class CreateNewTaskActivity : AppCompatActivity() {

    private lateinit var activityCreateNewTaskBinding: ActivityCreateNewTaskBinding
    lateinit var createNewTaskViewModel: CreateNewTaskViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val disposable = CompositeDisposable()
    lateinit var taskResponse: TaskResponse

    companion object {
        const val SPINNER_DEFAULT_VALUE = "Select the status of task"
        const val ACTION_BAR_COLOR = "#0E1227"
        const val SNACKBAR_COMPLETE_TASKS = "Complete all the fields provided"
        const val UPDATE = "Update"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)

        createNewTaskViewModel = ViewModelProviders.of(this, viewModelFactory).get(CreateNewTaskViewModel::class.java)
        activityCreateNewTaskBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_new_task)
        activityCreateNewTaskBinding.viewModel = createNewTaskViewModel

        initialize()
        setListeners()
    }

    private fun initialize() {
        val actionbar = supportActionBar
        actionbar?.setBackgroundDrawable(ColorDrawable(Color.parseColor(ACTION_BAR_COLOR)))
        actionbar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        if(intent.getBooleanExtra(ScrumBoardMainActivity.EDIT_TASK, false)) {
            taskResponse = intent.getSerializableExtra(ScrumBoardMainActivity.OLD_TASK_TO_UPDATE) as TaskResponse
            activityCreateNewTaskBinding.titleEt.setText(taskResponse.title)
            activityCreateNewTaskBinding.descriptionEt.setText(taskResponse.description)
            activityCreateNewTaskBinding.saveBtn.text = UPDATE
            val adapter =
                ArrayAdapter.createFromResource(this, R.array.states, android.R.layout.simple_spinner_item)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            activityCreateNewTaskBinding.statusSpinner.adapter = adapter
            val spinnerPosition = adapter.getPosition(taskResponse.status.toUpperCase())
            activityCreateNewTaskBinding.statusSpinner.setSelection(spinnerPosition)
        }
     }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setListeners() {
        if(intent.getBooleanExtra(ScrumBoardMainActivity.EDIT_TASK, false)) {
            activityCreateNewTaskBinding.saveBtn.setOnClickListener {
                postDataToServer(it, true)
            }
        }
        else {
            activityCreateNewTaskBinding.saveBtn.setOnClickListener {
                postDataToServer(it, false)
            }
        }
    }

    private fun postDataToServer(view: View, updateAction: Boolean) {
        val title = activityCreateNewTaskBinding.titleEt.text.toString()
        val description = activityCreateNewTaskBinding.descriptionEt.text.toString()
        val status = activityCreateNewTaskBinding.statusSpinner.selectedItem.toString()
        if(title.isEmpty() || description.isEmpty() || status == SPINNER_DEFAULT_VALUE) {
            Snackbar.make(view, SNACKBAR_COMPLETE_TASKS, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        else {
            val intent = Intent(this@CreateNewTaskActivity, ScrumBoardMainActivity::class.java)
            postTaskData(Task(title, description, status), intent, updateAction)
        }
    }

    fun postTaskData(taskToPost: Task, intent: Intent, updateAction: Boolean) {
        if(ConnectionUtils.isNetConnected(this)) {
            if(updateAction) {
                disposable.add(createNewTaskViewModel.updateTask(taskResponse.id, taskToPost)
                    .subscribe(
                        {
                            createNewTaskViewModel.handleSuccessResponse()
                            setResult(Activity.RESULT_OK, intent)
                            finish()
                            Log.d("responseUpdate", "" + it)
                        },
                        {
                            createNewTaskViewModel.handleFailedResponse()
                            Log.d("responseUpdateError", "" + it)
                        }
                    )
                )
            }
            else {
                disposable.add(createNewTaskViewModel.sendData(taskToPost)
                    .subscribe(
                        {
                            createNewTaskViewModel.handleSuccessResponse()
                            setResult(Activity.RESULT_OK, intent)
                            finish()
                            Log.d("response", "" + it)
                        },
                        {
                            createNewTaskViewModel.handleFailedResponse()
                            Log.d("response", "" + it)
                        }
                    )
                )
            }
        }
        else {
            createNewTaskViewModel.handleNoInternetResponse()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}
