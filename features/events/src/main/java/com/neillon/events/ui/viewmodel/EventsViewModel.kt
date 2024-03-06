package com.neillon.events.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.neillon.common.mvi.MviViewModel
import com.neillon.common.mvi.UIAction
import com.neillon.common.mvi.UIState
import com.neillon.events.domain.usecase.GetAllEventsUseCase
import com.neillon.events.domain.usecase.SearchEventsUseCase
import com.neillon.events.ui.mapper.mapToEventUI
import com.neillon.events.ui.model.EventUI
import kotlinx.coroutines.launch

class EventsViewModel(
    private val getAllEventsUseCase: GetAllEventsUseCase,
    private val searchEventsUseCase: SearchEventsUseCase
) : MviViewModel<EventsViewModel.Action, EventsViewModel.State>() {

    sealed class Action : UIAction {
        data object Load : Action()
        data class Search(val query: String) : EventsViewModel.Action()
    }

    sealed class State : UIState {
        data object Empty : State()
        data object Loading : State()
        data class Data(internal val items: List<EventUI>) : State()
        data class Error(val message: String) : State()
    }

    override fun createInitialState(): State = State.Empty

    override fun handleActions(action: Action) {
        when (action) {
            Action.Load -> viewModelScope.launch { loadEvents() }
            is Action.Search -> viewModelScope.launch { searchEvents(action.query) }
        }
    }

    private suspend fun loadEvents() = runCatching {
        setState { State.Loading }
        val events = getAllEventsUseCase.invoke().getOrThrow()
        setState { State.Data(events.mapToEventUI()) }
    }.getOrElse {
        setState { State.Error(it.message ?: "Error when loading events") }
    }

    private suspend fun searchEvents(query: String) = runCatching {
        setState { State.Loading }
        val events = searchEventsUseCase.invoke(query).getOrThrow()
        setState { State.Data(events.mapToEventUI()) }
    }.getOrElse {
        setState { State.Error("Error when searching events. " + it.message) }
    }
}