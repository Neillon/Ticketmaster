package com.neillon.common.network

import android.content.Context

interface ConnectivityChecker {
    val hasConnection: Boolean

    fun register(context: Context)
}