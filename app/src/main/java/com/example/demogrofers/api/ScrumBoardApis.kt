package com.example.demogrofers.api

import com.example.demogrofers.model.Task
import com.example.demogrofers.model.TaskResponse
import com.example.demogrofers.model.TaskToSearch
import io.reactivex.Single
import okhttp3.Call
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.*

interface ScrumBoardApis {

    @Headers(
        "access_token: f53d0522-34f9-46ed-a05a-052af9771606",
        "Access-Control-Allow-Origin: *",
        "Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Accept"
    )
    @GET("tasks")
    fun getAllCurrentTasks(): Single<Map<String, ArrayList<TaskResponse>>>


    @Headers(
        "access_token: f53d0522-34f9-46ed-a05a-052af9771606",
        "Access-Control-Allow-Origin: *",
        "Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Accept"
    )
    @POST("tasks")
    fun postNewTask(@Body task: Task): Single<TaskResponse>

    @Headers(
        "access_token: f53d0522-34f9-46ed-a05a-052af9771606",
        "Access-Control-Allow-Origin: *",
        "Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Accept"
    )
    @POST("task-filter/title")
    fun postTaskToSearch(@Body taskToSearch: TaskToSearch): Single<Map<String, ArrayList<TaskResponse>>>

    @Headers(
        "access_token: f53d0522-34f9-46ed-a05a-052af9771606",
        "Access-Control-Allow-Origin: *",
        "Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Accept"
    )
    @DELETE("tasks/{id}")
    fun deleteTask(@Path("id") id: Long): Single<retrofit2.Response<Void>>

    @Headers(
        "access_token: f53d0522-34f9-46ed-a05a-052af9771606",
        "Access-Control-Allow-Origin: *",
        "Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Accept"
    )
    @PUT("tasks/{id}")
    fun updateTask(@Path("id") id: Long, @Body task: Task): Single<TaskResponse>

}