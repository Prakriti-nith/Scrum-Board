package com.example.demogrofers.viewmodel

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.example.demogrofers.model.Task
import com.example.demogrofers.model.TaskResponse
import com.example.demogrofers.repository.ScrumBoardRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CreateNewTaskViewModel @Inject constructor(val repository: ScrumBoardRepository): ViewModel() {

    var progressState: ObservableField<Boolean> = ObservableField(false)
    var noResultState: ObservableField<Boolean> = ObservableField(false)
    var resultString: ObservableField<String> = ObservableField("")

    fun handleNoInternetResponse() {
        progressState.set(false)
        noResultState.set(true)
        resultString.set("No internet Connection")
    }

    fun sendData(taskToSend: Task): Single<TaskResponse> {
        progressState.set(true)
        return repository.postNewTask(taskToSend)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
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