package com.example.demogrofers.views

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
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
import com.example.demogrofers.viewmodel.CreateNewTaskViewModel
import com.example.demogrofers.viewmodel.ViewModelFactory
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class CreateNewTaskActivity : AppCompatActivity() {

    private lateinit var activityCreateNewTaskBinding: ActivityCreateNewTaskBinding
    lateinit var createNewTaskViewModel: CreateNewTaskViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val disposable = CompositeDisposable()

    companion object {
        const val SPINNER_DEFAULT_VALUE = "Select the status of task"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        initialize()

        activityCreateNewTaskBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_new_task)
        activityCreateNewTaskBinding.viewModel = createNewTaskViewModel

        setListeners()
    }

    private fun initialize() {
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)

        createNewTaskViewModel = ViewModelProviders.of(this, viewModelFactory).get(CreateNewTaskViewModel::class.java)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setListeners() {
        activityCreateNewTaskBinding.saveBtn.setOnClickListener {
            postDataToServer(it)
        }
    }

    private fun postDataToServer(view: View) {
        val title = activityCreateNewTaskBinding.titleEt.text.toString()
        val description = activityCreateNewTaskBinding.descriptionEt.text.toString()
        val status = activityCreateNewTaskBinding.statusSpinner.selectedItem.toString()
        if(title.isEmpty() || description.isEmpty() || status == SPINNER_DEFAULT_VALUE) {
            Snackbar.make(view, "Complete all the fields provided", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        else {
            val intent = Intent(this@CreateNewTaskActivity, ScrumBoardMainActivity::class.java)
            postTaskData(Task(title, description, status), intent)
        }
    }

    fun postTaskData(taskToPost: Task, intent: Intent) {
        if(ConnectionUtils.isNetConnected(this)) {

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
        else {
            createNewTaskViewModel.handleNoInternetResponse()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}
