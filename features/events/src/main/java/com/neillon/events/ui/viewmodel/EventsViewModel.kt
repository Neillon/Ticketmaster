package com.neillon.events.ui.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.neillon.common.mvi.MviViewModel
import com.neillon.common.mvi.UIAction
import com.neillon.common.mvi.UIState
import com.neillon.events.domain.usecase.GetAllEventsUseCase
import com.neillon.events.ui.mapper.mapToEventUI
import com.neillon.events.ui.model.EventUI
import kotlinx.coroutines.launch

class EventsViewModel(
    private val eventsUseCase: GetAllEventsUseCase
) : MviViewModel<EventsViewModel.Action, EventsViewModel.State>() {

    sealed class Action : UIAction {
        data object Load : Action()
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
        }
    }

    private suspend fun loadEvents() = runCatching {
        setState { State.Loading }
        val events = eventsUseCase.invoke().getOrThrow()
        setState { State.Data(events.mapToEventUI()) }
    }.getOrElse {
        setState { State.Error(it.message ?: "Error occurred") }
    }
}