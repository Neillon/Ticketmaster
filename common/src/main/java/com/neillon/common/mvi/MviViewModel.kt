package com.neillon.common.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class MviViewModel<Action: UIAction, State: UIState>: ViewModel() {
    private val initialState : State by lazy { createInitialState() }

    private val _state: MutableStateFlow<State> = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state.asStateFlow()

    private val _action: MutableSharedFlow<Action> = MutableSharedFlow()

    abstract fun createInitialState(): State
    abstract fun handleActions(action: Action)

    init {
        subscribeEvents()
    }

    private fun subscribeEvents() {
        viewModelScope.launch {
            _action.collect {
                handleActions(it)
            }
        }
    }

    fun doAction(newAction: Action) {
        viewModelScope.launch { _action.emit(newAction) }
    }

    protected fun setState(reduce: State.() -> State) {
        val newState = reduce(state.value)
        _state.value = newState
    }
}