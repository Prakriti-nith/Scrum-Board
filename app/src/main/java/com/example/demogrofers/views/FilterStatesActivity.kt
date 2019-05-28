package com.example.demogrofers.views

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import com.example.demogrofers.R
import com.example.demogrofers.databinding.ActivityFilterStatesBinding
import com.example.demogrofers.viewmodel.FilterStatesViewModel

class FilterStatesActivity : AppCompatActivity() {

    private lateinit var activityFilterStatesBinding: ActivityFilterStatesBinding
    private var checkedStates = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityFilterStatesBinding = DataBindingUtil.setContentView(this, R.layout.activity_filter_states)

        initialize()
        setListener()

        markCheckBoxes()
    }

    private fun markCheckBoxes() {
        var intent = getIntent()
        if(!intent.getBooleanExtra("firstTime", true)) {
            checkedStates = intent.getStringArrayListExtra("checkedCheckBoxes")
        }

        markCheckbox(activityFilterStatesBinding.chkRejected, "rejected")
        markCheckbox(activityFilterStatesBinding.chkPending, "pending")
        markCheckbox(activityFilterStatesBinding.chkDevelopment, "development")
        markCheckbox(activityFilterStatesBinding.chkTesting, "testing")
        markCheckbox(activityFilterStatesBinding.chkProduction, "production")
    }

    private fun markCheckbox(checkBox: CheckBox, state: String) {
        if(state in checkedStates) {
            checkBox.setChecked(true);
        }
        else {
            checkBox.setChecked(false);
        }
    }

    private fun initialize() {
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun filterState(checkBox: CheckBox, taskState: String) {
        if(checkBox.isChecked) {
            checkedStates.add(taskState)
        }
    }

    private fun setListener() {
        activityFilterStatesBinding.btnDisplay.setOnClickListener {

            filterState(activityFilterStatesBinding.chkRejected, "rejected")
            filterState(activityFilterStatesBinding.chkPending, "pending")
            filterState(activityFilterStatesBinding.chkDevelopment, "development")
            filterState(activityFilterStatesBinding.chkTesting, "testing")
            filterState(activityFilterStatesBinding.chkProduction, "production")

            val intent = Intent(this@FilterStatesActivity, ScrumBoardMainActivity::class.java)
            intent.putStringArrayListExtra("checkedCheckBoxes", checkedStates)
            intent.putExtra("fromFilterActivity", true)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
