package com.neillon.events.domain.repository

import com.neillon.events.domain.mdoel.Event

interface EventsRepository {
    suspend fun getEvents(): List<Event>
    suspend fun searchEvents(name: String): List<Event>
}