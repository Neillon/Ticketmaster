package com.neillon.events.domain.mdoel

import java.time.LocalDate

data class Event(
    val name: String,
    val imageUrl: String,
    val date: LocalDate,
    val place: String,
    val city: String,
    val state: String,
    val remoteId: String
)