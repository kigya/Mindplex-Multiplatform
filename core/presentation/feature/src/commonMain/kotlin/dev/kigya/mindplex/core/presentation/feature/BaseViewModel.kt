@file:Suppress("OptionalUnit")

package dev.kigya.mindplex.core.presentation.feature

import androidx.annotation.AnyThread
import androidx.annotation.CallSuper
import androidx.annotation.EmptySuper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kigya.mindplex.core.domain.interactor.runner.CoroutineUseCaseRunner
import dev.kigya.mindplex.core.presentation.common.extension.asStateFlowWithStartAction
import dev.kigya.mindplex.core.util.annotation.MarkerInterface
import dev.kigya.mindplex.navigation.navigator.navigator.MindplexNavigatorContract
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

@MarkerInterface
interface CopyableComponentState

abstract class BaseViewModel<STATE, EFFECT>(
    protected val navigatorContract: MindplexNavigatorContract,
    initialState: STATE,
) : ViewModel(), CoroutineUseCaseRunner where STATE : Any, STATE : CopyableComponentState {
    final override val useCaseCoroutineScope = viewModelScope

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<STATE> = _state.asStateFlowWithStartAction(
        scope = useCaseCoroutineScope,
        startAction = ::executeStartAction,
    )

    private val _effect = Channel<EFFECT>(capacity = BUFFERED)
    val effect: Flow<EFFECT> = _effect.receiveAsFlow()

    @AnyThread
    @EmptySuper
    protected open fun executeStartAction() = Unit

    @AnyThread
    protected fun updateState(transform: STATE.() -> STATE) = _state.update { it.transform() }

    @AnyThread
    protected fun getState(): STATE = _state.value

    @AnyThread
    protected fun sendEffect(effect: EFFECT) {
        _effect.trySend(effect)
    }

    @CallSuper
    override fun onCleared() {
        _effect.cancel()
        super.onCleared()
    }
}
