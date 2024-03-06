package com.neillon.events.ui.mapper

import com.neillon.events.domain.mdoel.Event
import com.neillon.events.ui.model.EventUI
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Event.mapToEventUI() = EventUI(
    name = this.name,
    imageUrl = this.imageUrl,
    date = formatDate(this.date),
    place = this.place,
    city = this.city,
    state = formatState(this.state),
    id = this.remoteId
)

fun formatState(state: String): String = if (state.contains("_")) {
    state.split("_").map { it.first() }.joinToString()
} else {
    state.substring(0..1)
}

fun formatDate(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("MMM dd", Locale.getDefault())
    return date.format(formatter)
}

fun List<Event>.mapToEventUI() = map { it.mapToEventUI() }