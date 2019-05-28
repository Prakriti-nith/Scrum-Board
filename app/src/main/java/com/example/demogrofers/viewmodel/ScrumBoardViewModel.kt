package com.example.demogrofers.viewmodel

import android.arch.lifecycle.ViewModel
import com.example.demogrofers.model.Task
import com.example.demogrofers.repository.ScrumBoardRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ScrumBoardViewModel: ViewModel() {

    var progressState: Boolean = false
    var noResultState: Boolean = false
    var resultString: String = ""

    fun handleNoInternetResponse() {
        progressState = false
        noResultState = true
        resultString = "No internet Connection"
    }

    fun loadData(repository: ScrumBoardRepository): Single<Map<String, ArrayList<Task>>> {

        progressState = true
        return repository.getScrumBoardRepository()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun handleSuccessResponse() {
        progressState = false
    }

    fun handleFailedResponse() {
        progressState = false
        noResultState = true
        resultString = "API not responding"
    }
}

