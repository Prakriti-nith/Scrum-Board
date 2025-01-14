package com.example.demogrofers.viewmodel

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.example.demogrofers.model.Task
import com.example.demogrofers.model.TaskResponse
import com.example.demogrofers.model.TaskToSearch
import com.example.demogrofers.repository.ScrumBoardRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import javax.inject.Inject

class ScrumBoardViewModel @Inject constructor(val repository: ScrumBoardRepository): ViewModel() {

    var progressState: ObservableField<Boolean> = ObservableField(false)
    var noResultState: ObservableField<Boolean> = ObservableField(false)
    var resultString: ObservableField<String> = ObservableField("")

    fun handleNoInternetResponse() {
        progressState.set(false)
        noResultState.set(true)
        resultString.set("No internet Connection")
    }

    fun loadData(): Single<Map<String, ArrayList<TaskResponse>>> {
        progressState.set(true)
        return repository.getScrumBoardRepository()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun sendData(taskToSearch: TaskToSearch): Single<Map<String, ArrayList<TaskResponse>>> {
        progressState.set(true)
        return repository.postTaskToSearch(taskToSearch)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun deleteTask(id: Long): Single<retrofit2.Response<Void>> {
        progressState.set(true)
        return repository.deleteTask(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun handleSuccessResponse() {
        progressState.set(false)
    }

    fun handleFailedResponse() {
        progressState.set(false)
        noResultState.set(false)
        resultString.set( "API not responding")
    }
}
