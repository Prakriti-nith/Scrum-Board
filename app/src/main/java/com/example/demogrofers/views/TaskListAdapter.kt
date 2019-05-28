package com.example.demogrofers.views

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.demogrofers.databinding.RecyclerviewItemBinding
import com.example.demogrofers.interfaces.ListListener
import com.example.demogrofers.model.Task


class TaskListAdapter(
    private val listener: ListListener,
    private var taskList: ArrayList<Task> = ArrayList()
) : RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    private lateinit var recyclerViewItemBinding: RecyclerviewItemBinding

    fun setItems(taskLis: ArrayList<Task>?) {
        taskLis?.let {
            taskList = taskLis
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        recyclerViewItemBinding = RecyclerviewItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(recyclerViewItemBinding)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bind(taskList[p1], listener)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    inner class ViewHolder(recyclerviewItemBinding: RecyclerviewItemBinding) :
        RecyclerView.ViewHolder(recyclerviewItemBinding.root) {

        fun bind(task: Task, listener: ListListener) {
           // itemView.setOnClickListener { listener.onItemClick(task) }
            recyclerViewItemBinding.titleTv.text = task.title
            recyclerViewItemBinding.descriptionTv.text = task.description
            recyclerViewItemBinding.statusTv.text = task.status
//            recyclerViewItemBinding.deleteIv.setOnClickListener {}
//            recyclerViewItemBinding.editIv.setOnClickListener {}
        }
    }
}