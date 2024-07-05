package dev.kigya.mindplex.core.presentation.feature

import androidx.lifecycle.ViewModel
import dev.kigya.mindplex.core.domain.interactor.runner.CoroutineUseCaseRunner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

interface CopyableComponentState

abstract class BaseViewModel<STATE, EFFECT>(
    initialState: STATE,
) : ViewModel(), CoroutineUseCaseRunner where STATE : Any, STATE : CopyableComponentState {

    override val useCaseCoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<STATE> = _state

    private val _effect = Channel<EFFECT>(capacity = BUFFERED)
    val effect: Flow<EFFECT> = _effect.receiveAsFlow()

    protected fun updateState(transform: STATE.() -> STATE) {
        _state.update { it.transform() }
    }

    protected fun getState(): STATE = _state.value

    protected fun sendEffect(effect: EFFECT) {
        _effect.trySend(effect)
    }

    override fun onCleared() {
        _effect.cancel()
    }
}
