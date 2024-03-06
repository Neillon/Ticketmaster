package com.neillon.common.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.core.content.ContextCompat

private const val TAG = "ConnectivityChecker"

class ConnectivityCheckerImpl : ConnectivityChecker {
    override var hasConnection: Boolean = false

    override fun register(context: Context) {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            // network is available for use
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                Log.i(TAG, "onAvailable: Has connectivity")
                hasConnection = true
            }

            // Network capabilities have changed for the network
            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                super.onCapabilitiesChanged(network, networkCapabilities)
                val unmetered =
                    networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
                Log.i(TAG, "onCapabilitiesChanged: Connectivity changed")
            }

            // lost network connection
            override fun onLost(network: Network) {
                super.onLost(network)
                Log.i(TAG, "onLost: Lost connectivity")
                hasConnection = false
            }
        }

        val connectivityManager = ContextCompat.getSystemService(
            context,
            ConnectivityManager::class.java
        ) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)
    }
}