package com.neillon.events.data.datasources.remote

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("_embedded")
    val embedded: T
)
