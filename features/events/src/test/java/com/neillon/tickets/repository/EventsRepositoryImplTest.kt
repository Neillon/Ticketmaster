package com.neillon.tickets.repository

import android.content.res.TypedArray
import com.neillon.common.network.ConnectivityChecker
import com.neillon.events.data.datasources.local.EventsDatabase
import com.neillon.events.data.datasources.remote.ApiResponse
import com.neillon.events.data.datasources.remote.EventsApiResponse
import com.neillon.events.data.datasources.remote.EventsService
import com.neillon.events.data.mapper.mapToEntity
import com.neillon.events.data.mapper.mapToEvent
import com.neillon.events.data.repository.EventsRepositoryImpl
import com.neillon.tickets.helper.EventsMockHelper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class EventsRepositoryImplTest {

    private val connectivityChecker: ConnectivityChecker = mockk()
    private val eventsService: EventsService = mockk()
    private val eventsDatabase: EventsDatabase = mockk(relaxed = true)

    private lateinit var repository: EventsRepositoryImpl

    @Before
    fun setUp() {
        every { connectivityChecker.hasConnection } returns true

        coEvery { eventsService.getEvents(any()) } returns API_RESPONSE

        with(eventsDatabase) {
            coEvery { eventsDao().getAll() } returns EventsMockHelper.EVENTS.mapToEntity()
        }

        repository = EventsRepositoryImpl(connectivityChecker, eventsService, eventsDatabase)
    }

    @Test
    fun `When connectivity is available, should fetch events and store`() = runTest {
        val result = repository.getEvents()

        assertEquals(result, API_RESPONSE.mapToEvent())
        coVerify { eventsDatabase.eventsDao().insertAll(any(), any(), any()) }
    }


    @Test
    fun `When no connectivity, should fetch events from database`() = runTest {
        every { connectivityChecker.hasConnection } returns false

        val result = repository.getEvents()

        assertEquals(result, EventsMockHelper.EVENTS)
        coVerify { eventsDatabase.eventsDao().getAll() }
        coVerify(exactly = 0) { eventsService.getEvents(any()) }
    }

    companion object {
        private val API_RESPONSE = ApiResponse(EventsApiResponse(EventsMockHelper.EVENTS_DTO))
    }
}