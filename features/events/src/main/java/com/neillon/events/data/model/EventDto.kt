package com.neillon.events.data.model

import com.google.gson.annotations.SerializedName

data class EventDto(
    val id: String,
    val name: String,
    val dates: EventDateDto,
    val images: List<EventImageDto>,
    @SerializedName("_embedded")
    val info: EventInfoDto
)