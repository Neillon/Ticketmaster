package com.neillon.events.ui.model

import java.time.LocalDate

data class EventUI(
    val name: String,
    val imageUrl: String,
    val date: String,
    val place: String,
    val city: String,
    val state: String
)