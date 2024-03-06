package com.neillon.events.data.mapper

import com.neillon.events.data.datasources.local.EventsEntity
import com.neillon.events.data.datasources.remote.ApiResponse
import com.neillon.events.data.datasources.remote.EventsApiResponse
import com.neillon.events.data.model.EventDto
import com.neillon.events.domain.mdoel.Event
import java.time.LocalDate

fun ApiResponse<EventsApiResponse>.mapToEvent(): List<Event> =
    this.embedded.events.map { it.mapToEvent() }

fun EventDto.mapToEvent() = Event(
    name = this.name,
    imageUrl = this.images.firstOrNull { it.ratio == "16_9" }?.url ?: this.images.first().url,
    date = LocalDate.parse(this.dates.start.localDate),
    place = this.info.venues.first().place,
    city = this.info.venues.first().city.name,
    state = this.info.venues.first().state.name
)

fun List<EventsEntity>.mapToEvent() = map { it.mapToEvent() }
fun EventsEntity.mapToEvent() = Event(
    name = this.name,
    imageUrl = this.imageUrl,
    date = this.date,
    place = this.place,
    city = this.city,
    state = this.state
)

fun List<Event>.mapToEntity() = map { it.mapToEntity() }
fun Event.mapToEntity() = EventsEntity(
    name = this.name,
    imageUrl = this.imageUrl,
    date = this.date,
    place = this.place,
    city = this.city,
    state = this.state
)

