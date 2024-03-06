package com.neillon.tickets.viewmodel

import com.neillon.events.domain.usecase.GetAllEventsUseCase
import com.neillon.events.ui.viewmodel.EventsViewModel
import com.neillon.tickets.helper.CoroutinesTestRule
import com.neillon.tickets.helper.EventsMockHelper
import com.neillon.tickets.helper.test
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EventsViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val useCase: GetAllEventsUseCase = mockk()
    private lateinit var viewModel: EventsViewModel

    @Before
    fun setUp() {
        coEvery { useCase.invoke() } returns Result.success(EventsMockHelper.EVENTS)

        viewModel = EventsViewModel(useCase)
    }

    @Test
    fun `When action is Load and, useCase returns success, should update view state to data`() =
        runTest {
            val observer = viewModel.state.test(this)
            viewModel.doAction(EventsViewModel.Action.Load)
            observer.assertValueCount(2)
                .assertValueAt(0) { it is EventsViewModel.State.Empty }
                .assertValueAt(1) { it is EventsViewModel.State.Data }
                .dispose()
        }

    @Test
    fun `When action is Load and, useCase returns error, should update view state to error`() =
        runTest {
            coEvery { useCase.invoke() } returns Result.failure(Exception())
            val observer = viewModel.state.test(this)
            viewModel.doAction(EventsViewModel.Action.Load)
            observer.assertValueCount(2)
                .assertValueAt(0) { it is EventsViewModel.State.Empty }
                .assertValueAt(1) { it is EventsViewModel.State.Error }
                .dispose()
        }


    @Test
    fun `When useCase throws exception, should update view state to error`() =
        runTest {
            coEvery { useCase.invoke() } throws Exception()
            val observer = viewModel.state.test(this)
            viewModel.doAction(EventsViewModel.Action.Load)
            observer.assertValueCount(2)
                .assertValueAt(0) { it is EventsViewModel.State.Empty }
                .assertValueAt(1) { it is EventsViewModel.State.Error }
                .dispose()
        }
}