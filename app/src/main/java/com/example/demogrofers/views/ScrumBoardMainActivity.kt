package com.example.demogrofers.views

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.support.v4.widget.DrawerLayout
import android.support.design.widget.NavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.example.demogrofers.ConnectionUtils
import com.example.demogrofers.R
import com.example.demogrofers.ScrumBoardApplication
import com.example.demogrofers.api.ScrumBoardApis
import com.example.demogrofers.databinding.ActivityMainBinding
import com.example.demogrofers.interfaces.ListListener
import com.example.demogrofers.model.Task
import com.example.demogrofers.repository.ScrumBoardRepository
import com.example.demogrofers.viewmodel.ScrumBoardViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.content_main.view.*

class ScrumBoardMainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, ListListener {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var scrumBoardViewModel: ScrumBoardViewModel
    private var taskListAdapter: TaskListAdapter? = null
    private val disposable = CompositeDisposable()
    private lateinit var repository: ScrumBoardRepository
    private var checkedTasksArrayList = ArrayList<Task>()
    private var checkedStatesStringArray = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scrumBoardViewModel = ViewModelProviders.of(this).get(ScrumBoardViewModel::class.java)

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        activityMainBinding.viewModel = ScrumBoardViewModel()

        initialize()
        setListener()

        repository = ScrumBoardRepository(ScrumBoardApplication.getRetrofitInstance().create(ScrumBoardApis::class.java))
        taskListAdapter = TaskListAdapter(this)
        activityMainBinding.appBar.mainContent.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        activityMainBinding.appBar.mainContent.recyclerView.adapter = taskListAdapter


        getTaskListData()
        populateCheckedStatesArrayList()
    }

    private fun populateCheckedStatesArrayList() {
        checkedStatesStringArray.add("rejected")
        checkedStatesStringArray.add("pending")
        checkedStatesStringArray.add("development")
        checkedStatesStringArray.add("testing")
        checkedStatesStringArray.add("production")
    }

    private fun getTaskListData() {
        if(ConnectionUtils.isNetConnected(this)) {

            disposable.add(scrumBoardViewModel.loadData(repository)
                .subscribe(
                    {
                        Log.d("response", "getting response")
                        scrumBoardViewModel.handleSuccessResponse()
                        for(mapStatus in it) {
                           checkedTasksArrayList.addAll(ArrayList(mapStatus.value))
                        }
//                        val intent = getIntent()
//                        if(intent.getBooleanExtra("fromFilterActivity", false)) {
//                            checkedStatesStringArray = intent.getStringArrayListExtra("checkedCheckBoxes")
//                            for(task in it) {
//                                if(task.status.toLowerCase() in checkedStatesStringArray) {
//                                    checkedTasksArrayList.add(task)
//                                }
//                            }
//                        }
//                        else {
//                            checkedTasksArrayList = it
//                        }
//                        taskListAdapter?.setItems(checkedTasksArrayList)
                        taskListAdapter?.setItems(checkedTasksArrayList)
                    },
                    {
                        scrumBoardViewModel.handleFailedResponse()
                        scrumBoardViewModel.noResultState = false
                        Log.d("response", "no response" + it)
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

            val receivedIntent = getIntent()

            val intent = Intent(this@ScrumBoardMainActivity, FilterStatesActivity::class.java)
            if(receivedIntent.getBooleanExtra("fromFilterActivity", false)) {
                intent.putStringArrayListExtra("checkedCheckBoxes", checkedStatesStringArray)
                intent.putExtra("firstTime", false)
            }
            else {
                intent.putExtra("firstTime", true)
            }
            startActivity(intent)
        }
    }

    private fun initialize() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle the camera action
            }
            R.id.nav_logout -> {

            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onItemClick(task: Task) {
        TODO("not implemented") //What happens when we click on a list item
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}
