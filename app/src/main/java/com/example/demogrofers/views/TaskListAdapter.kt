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
                            getTaskListData()
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
    }

    private fun getTaskListData() {
        if(ConnectionUtils.isNetConnected(context)) {
            disposable.add(scrumBoardViewModel.loadData()
                .subscribe(
                    {
                        scrumBoardViewModel.handleSuccessResponse()
                        Log.d("response", "response received")
                        val checkedTasksArrayList = ArrayList<TaskResponse>()
                        for(mapStatus in it) {
//                            if(mapStatus.key.toLowerCase() in checkedStatesStringArray) {
                                checkedTasksArrayList.addAll(ArrayList(mapStatus.value))
//                            }
                        }
                        setItems(checkedTasksArrayList)
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

    override fun getItemCount(): Int {
        return taskList.size
    }

    inner class ViewHolder(val recyclerviewItemBinding: RecyclerviewItemBinding) :
        RecyclerView.ViewHolder(recyclerviewItemBinding.root)
}
