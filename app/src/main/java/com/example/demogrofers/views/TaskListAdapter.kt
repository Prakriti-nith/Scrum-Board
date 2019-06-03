package com.example.demogrofers.views

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.demogrofers.databinding.RecyclerviewItemBinding
import com.example.demogrofers.model.Task
import com.example.demogrofers.model.TaskResponse


class TaskListAdapter(
    private var taskList: ArrayList<TaskResponse> = ArrayList()
) : RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {


    fun setItems(taskLis: ArrayList<TaskResponse>?) {
        taskLis?.let {
            taskList.clear()
            taskList.addAll(it)
            Log.d("searchresponseList", "Search Response: "+ taskList)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val recyclerViewItemBinding = RecyclerviewItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(recyclerViewItemBinding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val task = taskList[position]
        val recyclerViewItemBinding = viewHolder.recyclerviewItemBinding
        Log.d("searchresponseTask", "Search Response: "+ task)
        recyclerViewItemBinding.titleTv.text = task.title
        recyclerViewItemBinding.descriptionTv.text = task.description
        recyclerViewItemBinding.statusTv.text = task.status
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    inner class ViewHolder(val recyclerviewItemBinding: RecyclerviewItemBinding) :
        RecyclerView.ViewHolder(recyclerviewItemBinding.root)
}
