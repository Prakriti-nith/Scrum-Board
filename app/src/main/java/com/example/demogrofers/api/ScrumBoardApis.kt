package com.example.demogrofers.api

import com.example.demogrofers.model.Task
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers

interface ScrumBoardApis {

//    @Headers(
//        "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbnVyYWciLCJleHAiOjE1NTk4OTAyNDB9.DzA2KRKqkXPqeLQ9D7V_J1ln8za69VyjC6urRBKn82doX8HW_EV8TOydHC4Axe9_gexb11jqiLmvY93YBre9Zg",
//        "Access-Control-Allow-Origin: *",
//        "Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Accept"
//    )
    @GET("5ced4658b4565f19480609a5")
    fun getAllCurrentTasks(): Single<Map<String, ArrayList<Task>>>

}