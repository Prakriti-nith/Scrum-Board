package com.example.demogrofers

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object ConnectionUtils {

    fun isNetConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return !(networkInfo == null || !networkInfo.isConnectedOrConnecting)
    }
}