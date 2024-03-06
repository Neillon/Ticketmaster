package com.neillon.events.data.datasources.remote

import com.neillon.events.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface EventsService {
    @GET("discovery/v2/events.json")
    suspend fun getEvents(
        @Query("apikey") apiKey: String = BuildConfig.API_KEY
    ): ApiResponse<EventsApiResponse>
}