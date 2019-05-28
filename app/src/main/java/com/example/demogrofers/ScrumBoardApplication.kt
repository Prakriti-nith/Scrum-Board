package com.example.demogrofers

import android.app.Application
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ScrumBoardApplication: Application() {

    companion object {
//        private const val BASE_URL="http://192.168.36.64:8080/user/"
        private const val BASE_URL="https://api.jsonbin.io/b/"
        private  var retrofit: Retrofit =  Retrofit.Builder()
                                        .baseUrl(BASE_URL)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                        .build()


        fun getRetrofitInstance() = retrofit
    }

}