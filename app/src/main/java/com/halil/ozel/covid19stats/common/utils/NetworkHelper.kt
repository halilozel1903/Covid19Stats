package com.halil.ozel.covid19stats.common.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkHelper @Inject constructor(@ApplicationContext private val context: Context) {

    @SuppressLint("ObsoleteSdkInt")
    fun isInternetAvailable(): Boolean {

        var networkResult = false

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            val networkCapabilities = connectivityManager.activeNetwork ?: return false

            val activeNetwork = connectivityManager.getNetworkCapabilities(networkCapabilities)
                ?: return false
            networkResult = when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true

                else -> false
            }

        } else {
            @Suppress("DEPRECATED")
            connectivityManager.run {

                connectivityManager.activeNetworkInfo?.run {
                    networkResult = when (type) {
                        ConnectivityManager.TYPE_ETHERNET -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_VPN -> true
                        else -> false
                    }
                }
            }
        }
        return networkResult
    }
}