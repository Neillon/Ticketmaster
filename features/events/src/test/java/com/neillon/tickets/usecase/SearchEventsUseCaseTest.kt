package com.neillon.tickets.usecase

import com.neillon.events.domain.repository.EventsRepository
import com.neillon.events.domain.usecase.GetAllEventsUseCase
import com.neillon.events.domain.usecase.SearchEventsUseCase
import com.neillon.tickets.helper.CoroutinesTestRule
import com.neillon.tickets.helper.EventsMockHelper
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchEventsUseCaseTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val repository: EventsRepository = mockk()

    private lateinit var useCase: SearchEventsUseCase

    @Before
    fun setUp() {
        coEvery { repository.getEvents() } returns EventsMockHelper.EVENTS
        coEvery { repository.searchEvents(any()) } returns EventsMockHelper.EVENTS

        useCase = SearchEventsUseCase(repository)
    }

    @Test
    fun `UseCase returns successfully`() = runTest {
        val result = useCase.invoke("anyString")

        assertEquals(Result.success(EventsMockHelper.EVENTS), result)
    }

    @Test
    fun `UseCase handle errors`() = runTest {
        coEvery { repository.searchEvents(any()) } throws Exception()
        val result = useCase.invoke("anyString")

        assertTrue(result.isFailure)
    }
}