package com.example.demogrofers.views

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.demogrofers.ConnectionUtils
import com.example.demogrofers.databinding.ActivityMainBinding
import com.example.demogrofers.model.Task
import com.example.demogrofers.utils.FilterStates
import com.example.demogrofers.viewmodel.ScrumBoardViewModel
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import com.example.demogrofers.viewmodel.ViewModelFactory


class ScrumBoardMainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    lateinit var scrumBoardViewModel: ScrumBoardViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var taskListAdapter: TaskListAdapter? = null
    private val disposable = CompositeDisposable()
   // private lateinit var repository: ScrumBoardRepository
    private var checkedStatesStringArray: ArrayList<String> = ArrayList()

    companion object {
        const val REQUEST_CODE_FILTER_ACTIVITY = 9
        const val REQUEST_CODE_NEW_TASK_ACTIVITY = 5
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)

        initializeViewModel()
//        scrumBoardViewModel = ViewModelProviders.of(this).get(ScrumBoardViewModel::class.java)
        activityMainBinding = DataBindingUtil.setContentView(this, com.example.demogrofers.R.layout.activity_main)
        activityMainBinding.viewModel = scrumBoardViewModel

        setListener()

       // repository = ScrumBoardRepository(ScrumBoardApplication.getRetrofitInstance().create(ScrumBoardApis::class.java))
        taskListAdapter = TaskListAdapter()
        activityMainBinding.appBar.mainContent.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        activityMainBinding.appBar.mainContent.recyclerView.adapter = taskListAdapter

        populateCheckedStatesArrayList()
        getTaskListData()

    }

    private fun initializeViewModel() {
        scrumBoardViewModel = ViewModelProviders.of(this, viewModelFactory).get(ScrumBoardViewModel::class.java)
    }

    private fun populateCheckedStatesArrayList() {
        checkedStatesStringArray.add(FilterStates.REJECTED)
        checkedStatesStringArray.add(FilterStates.PENDING)
        checkedStatesStringArray.add(FilterStates.DEVELOPMENT)
        checkedStatesStringArray.add(FilterStates.TESTING)
        checkedStatesStringArray.add(FilterStates.PRODUCTION)
    }

    private fun getTaskListData() {
        if(ConnectionUtils.isNetConnected(this)) {

            disposable.add(scrumBoardViewModel.loadData()
                .subscribe(
                    {
                        scrumBoardViewModel.handleSuccessResponse()
                        Log.d("response", "response received")

                        val checkedTasksArrayList = ArrayList<Task>()
                        for(mapStatus in it) {
                            if(mapStatus.key.toLowerCase() in checkedStatesStringArray) {
                                checkedTasksArrayList.addAll(ArrayList(mapStatus.value))
                            }
                        }

                        taskListAdapter?.setItems(checkedTasksArrayList)
                    },
                    {
                        Log.d("response", " no response" + it)
                        scrumBoardViewModel.handleFailedResponse()
                    }
                )

            )

        }
        else {
            scrumBoardViewModel.handleNoInternetResponse()
        }
    }

    private fun setListener() {
        activityMainBinding.appBar.filterTv.setOnClickListener {

            val intent = Intent(this@ScrumBoardMainActivity, FilterStatesActivity::class.java)
            intent.putStringArrayListExtra(FilterStatesActivity.CHECKED_STATES_STRING_ARRAY, checkedStatesStringArray)
            startActivityForResult(intent, REQUEST_CODE_FILTER_ACTIVITY)
        }
        activityMainBinding.appBar.fab.setOnClickListener {
            val intent = Intent(this@ScrumBoardMainActivity, CreateNewTaskActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_NEW_TASK_ACTIVITY)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_CODE_FILTER_ACTIVITY && resultCode == Activity.RESULT_OK) {
            data?.let {
                it.getStringArrayListExtra(FilterStatesActivity.CHECKED_STATES_STRING_ARRAY)?.let { statesArray ->
                    checkedStatesStringArray = statesArray
                }
            }
            getTaskListData()
        }

        if(requestCode == REQUEST_CODE_NEW_TASK_ACTIVITY && resultCode == Activity.RESULT_OK) {
            data?.let {
                val taskToPost:Task = it.getSerializableExtra(CreateNewTaskActivity.HASH_MAP_POST_REQUEST) as Task
                postTaskData(taskToPost)
            }
        }
    }

    fun postTaskData(taskToPost: Task) {
        if(ConnectionUtils.isNetConnected(this)) {

            disposable.add(scrumBoardViewModel.sendData(taskToPost)
                .subscribe(
                    {
                        scrumBoardViewModel.handleSuccessResponse()
                        getTaskListData()
                        Log.d("response", "" + it)
                    },
                    {
                        scrumBoardViewModel.handleFailedResponse()
                    }
                )

            )

        }
        else {
            scrumBoardViewModel.handleNoInternetResponse()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}
