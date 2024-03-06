package com.neillon.events.data.datasources.remote

import com.neillon.events.data.model.EventDto

data class EventsApiResponse(
    val events: List<EventDto>
)