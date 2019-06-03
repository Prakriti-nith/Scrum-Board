package com.example.demogrofers.api

import com.example.demogrofers.model.Task
import com.example.demogrofers.model.TaskResponse
import com.example.demogrofers.model.TaskToSearch
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ScrumBoardApis {

    @Headers(
        "access_token: 56511384-f662-4188-8f7c-ad941b9bd705",
        "Access-Control-Allow-Origin: *",
        "Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Accept"
    )
    @GET("tasks")
    fun getAllCurrentTasks(): Single<Map<String, ArrayList<TaskResponse>>>


    @Headers(
        "access_token: 56511384-f662-4188-8f7c-ad941b9bd705",
        "Access-Control-Allow-Origin: *",
        "Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Accept"
    )
    @POST("tasks")
    fun postNewTask(@Body task: Task): Single<TaskResponse>

    @Headers(
        "access_token: 56511384-f662-4188-8f7c-ad941b9bd705",
        "Access-Control-Allow-Origin: *",
        "Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Accept"
    )
    @POST("task-filter/title")
    fun postTaskToSearch(@Body taskToSearch: TaskToSearch): Single<Map<String, ArrayList<TaskResponse>>>

}