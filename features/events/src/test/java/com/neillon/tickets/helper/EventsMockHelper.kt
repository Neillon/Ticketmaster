package com.neillon.tickets.helper

import com.google.gson.annotations.SerializedName
import com.neillon.events.data.model.EventCityDto
import com.neillon.events.data.model.EventDateDto
import com.neillon.events.data.model.EventDto
import com.neillon.events.data.model.EventImageDto
import com.neillon.events.data.model.EventInfoDto
import com.neillon.events.data.model.EventStartDateDto
import com.neillon.events.data.model.EventStateDto
import com.neillon.events.data.model.EventVenueDto
import com.neillon.events.domain.mdoel.Event
import java.time.LocalDate

object EventsMockHelper {
    val EVENT_DTO = EventDto(
        id = "Id",
        name = "Name",
        dates = EventDateDto(EventStartDateDto("2024-12-12", "12:00")),
        images = listOf(EventImageDto("16_9", "url", 120, 120, false)),
        info = EventInfoDto(
            listOf(
                EventVenueDto(
                    "Place",
                    EventCityDto("City"),
                    EventStateDto("State")
                )
            )
        )
    )

    val EVENT = Event(
        name = "Name",
        imageUrl = "url",
        date = LocalDate.now(),
        place = "Place",
        city = "City",
        state = "State"
    )

    val EVENTS: List<Event> = listOf(EVENT, EVENT, EVENT)

    val EVENTS_DTO: List<EventDto> = listOf(EVENT_DTO, EVENT_DTO, EVENT_DTO)
}