package com.example.demogrofers.views

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.demogrofers.ConnectionUtils
import com.example.demogrofers.databinding.RecyclerviewItemBinding
import com.example.demogrofers.model.TaskResponse
import com.example.demogrofers.viewmodel.ScrumBoardViewModel
import io.reactivex.disposables.CompositeDisposable
import android.content.Intent


class TaskListAdapter(
    private var context: Context,
    private var scrumBoardViewModel: ScrumBoardViewModel,
    private var disposable: CompositeDisposable,
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
        recyclerViewItemBinding.deleteIv.setOnClickListener {
            if(ConnectionUtils.isNetConnected(context)) {
                disposable.add(scrumBoardViewModel.deleteTask(task.id)
                    .subscribe(
                        {
                            scrumBoardViewModel.handleSuccessResponse()
                            Log.d("delete", ""+ position)
                            if (position != RecyclerView.NO_POSITION) {
                                taskList.removeAt(position)
                                notifyItemRemoved(position)
                            }
                        },
                        {
                            scrumBoardViewModel.handleFailedResponse()
                            Log.d("response", "" + it)
                        }
                    )
                )
            }
            else {
                scrumBoardViewModel.handleNoInternetResponse()
            }
        }
        recyclerViewItemBinding.editIv.setOnClickListener {
            val activity = context as ScrumBoardMainActivity
            val intent = Intent(context, CreateNewTaskActivity::class.java)
            intent.putExtra(ScrumBoardMainActivity.OLD_TASK_TO_UPDATE, task)
            intent.putExtra(ScrumBoardMainActivity.EDIT_TASK, true)
            activity.startActivityForResult(intent, ScrumBoardMainActivity.REQUEST_CODE_UPDATE_TASK)
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    inner class ViewHolder(val recyclerviewItemBinding: RecyclerviewItemBinding) :
        RecyclerView.ViewHolder(recyclerviewItemBinding.root)
}
