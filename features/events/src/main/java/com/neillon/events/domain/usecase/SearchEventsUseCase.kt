package com.neillon.events.domain.usecase

import com.neillon.events.domain.mdoel.Event
import com.neillon.events.domain.repository.EventsRepository

class SearchEventsUseCase(
    private val repository: EventsRepository
) {
    suspend fun invoke(query: String) = runCatching { repository.searchEvents(query) }
}