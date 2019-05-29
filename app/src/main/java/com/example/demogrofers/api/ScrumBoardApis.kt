package com.example.demogrofers.api

import com.example.demogrofers.model.Task
import com.example.demogrofers.model.TaskResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ScrumBoardApis {

    @Headers(
        "access_token: 2e3195db-2a60-4544-920b-ab4b0fbdaf5f",
        "Access-Control-Allow-Origin: *",
        "Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Accept"
    )
    @GET("tasks")
    fun getAllCurrentTasks(): Single<Map<String, ArrayList<Task>>>


    @Headers(
        "access_token: 2e3195db-2a60-4544-920b-ab4b0fbdaf5f",
        "Access-Control-Allow-Origin: *",
        "Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Accept"
    )
    @POST("tasks")
    fun postNewTask(@Body task: Task): Single<TaskResponse>

}