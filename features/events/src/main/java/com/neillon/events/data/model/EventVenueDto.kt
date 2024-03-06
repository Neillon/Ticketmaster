package com.neillon.events.data.model

import com.google.gson.annotations.SerializedName

data class EventVenueDto(
    @SerializedName("name")
    val place: String,
    val city: EventCityDto,
    val state: EventStateDto,
)