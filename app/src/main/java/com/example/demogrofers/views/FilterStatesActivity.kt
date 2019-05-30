package com.example.demogrofers.views

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import com.example.demogrofers.R
import com.example.demogrofers.databinding.ActivityFilterStatesBinding
import com.example.demogrofers.utils.FilterStates

class FilterStatesActivity : AppCompatActivity() {

    private lateinit var activityFilterStatesBinding: ActivityFilterStatesBinding
    private var checkedStates: ArrayList<String> = ArrayList()

    companion object {
        const val CHECKED_STATES_STRING_ARRAY = "checkedStatesStringArray"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityFilterStatesBinding = DataBindingUtil.setContentView(this, R.layout.activity_filter_states)

        initialize()
        setListener()

    }

    private fun initialize() {
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        checkedStates = intent.getStringArrayListExtra(CHECKED_STATES_STRING_ARRAY)

        markCheckbox(activityFilterStatesBinding.chkRejected, FilterStates.REJECTED)
        markCheckbox(activityFilterStatesBinding.chkPending, FilterStates.PENDING)
        markCheckbox(activityFilterStatesBinding.chkDevelopment, FilterStates.DEVELOPMENT)
        markCheckbox(activityFilterStatesBinding.chkTesting, FilterStates.TESTING)
        markCheckbox(activityFilterStatesBinding.chkProduction, FilterStates.PRODUCTION)
    }

    private fun markCheckbox(checkBox: CheckBox, state: String) {
        checkBox.isChecked = state in checkedStates
    }

    private fun setListener() {
        activityFilterStatesBinding.btnDisplay.setOnClickListener {
            checkedStates.clear()
            filterState(activityFilterStatesBinding.chkRejected, FilterStates.REJECTED)
            filterState(activityFilterStatesBinding.chkPending, FilterStates.PENDING)
            filterState(activityFilterStatesBinding.chkDevelopment, FilterStates.DEVELOPMENT)
            filterState(activityFilterStatesBinding.chkTesting, FilterStates.TESTING)
            filterState(activityFilterStatesBinding.chkProduction, FilterStates.PRODUCTION)

            val intent = Intent(this@FilterStatesActivity, ScrumBoardMainActivity::class.java)
            intent.putStringArrayListExtra(CHECKED_STATES_STRING_ARRAY, checkedStates)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    private fun filterState(checkBox: CheckBox, taskState: String) {
        if(checkBox.isChecked) {
            checkedStates.add(taskState)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
