package com.neillon.events.data.repository

import com.neillon.common.network.ConnectivityChecker
import com.neillon.events.data.datasources.local.EventsDatabase
import com.neillon.events.data.datasources.remote.EventsService
import com.neillon.events.data.mapper.mapToEntity
import com.neillon.events.data.mapper.mapToEvent
import com.neillon.events.domain.mdoel.Event
import com.neillon.events.domain.repository.EventsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EventsRepositoryImpl(
    private val connectivityChecker: ConnectivityChecker,
    private val eventsService: EventsService,
    private val eventsDatabase: EventsDatabase
) : EventsRepository {
    override suspend fun getEvents(): List<Event> = withContext(Dispatchers.IO) {
        if (connectivityChecker.hasConnection) {
            eventsService.getEvents().mapToEvent()
                .also { events -> eventsDatabase.eventsDao().insertAll(*events.mapToEntity().toTypedArray()) }
        } else {
            eventsDatabase.eventsDao().getAll().mapToEvent()
        }
    }

    override suspend fun searchEvents(name: String): List<Event> {
        return eventsDatabase.eventsDao().searchByName(name).mapToEvent()
    }
}