package com.neillon.events.domain.usecase

import com.neillon.events.domain.mdoel.Event
import com.neillon.events.domain.repository.EventsRepository

class GetAllEventsUseCase(
    private val repository: EventsRepository
) {
    suspend fun invoke(): Result<List<Event>> = runCatching { repository.getEvents() }
}