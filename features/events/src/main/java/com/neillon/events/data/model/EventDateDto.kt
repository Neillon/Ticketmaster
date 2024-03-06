package com.neillon.events.data.model

data class EventDateDto(
    val start: EventStartDateDto
)

data class EventStartDateDto(
    val localDate: String,
    val localTime: String
)
