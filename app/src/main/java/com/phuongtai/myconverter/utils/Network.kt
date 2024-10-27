package com.phuongtai.myconverter.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import javax.inject.Inject

interface INetworkConnectivity {
    fun getNetworkInfo() : NetworkInfo?
    fun isConnected() : Boolean
}

class Network @Inject constructor(val context: Context) :  INetworkConnectivity {
    /// Get network info
    override fun getNetworkInfo(): NetworkInfo? {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo
    }

    /// Check if the device is connected to the internet
    override fun isConnected(): Boolean {
        val info = getNetworkInfo()
        return info != null && info.isConnected
    }

}
