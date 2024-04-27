package dev.kigya.mindplex.core.presentation.feature.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import dev.kigya.core.domain.interactor.runner.CoroutineInteractorRunner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

/**
 * A marker interface that signifies that a state used in [BaseComponent] can be copied or transformed.
 * Implementing this interface in a state class allows the [BaseComponent] to generically handle
 * state transformations, as well as invoke *updateState()* function with syntax sugar of updating state.
 */
interface CopyableComponentState

/**
 * A base class for components in an application that maintains state and handles effects.
 * It provides lifecycle awareness through integration with [ComponentContext] and manages
 * coroutine scopes for executing asynchronous and non-blocking operations.
 *
 * This class is abstract and should be extended by specific components that manage their
 * own state and effects, encapsulating the business logic and interaction patterns.
 *
 * Type Parameters:
 * @param STATE The type of the state managed by this component. Must be a subtype of [Any] and [CopyableComponentState].
 * @param EFFECT The type of the effects that can be emitted by this component.
 *
 * @property componentContext The context associated with this component, typically provided by a UI or application framework.
 * @property initialState The initial state of the component when it is created.
 */
abstract class BaseComponent<STATE, EFFECT>(
    protected val componentContext: ComponentContext,
    initialState: STATE,
) : ComponentContext by componentContext,
    CoroutineInteractorRunner,
    InstanceKeeper.Instance where STATE : Any, STATE : CopyableComponentState {

    /**
     * A coroutine scope for use cases that should operate on the main thread
     * but can be cancelled individually without affecting the UI directly.
     */
    override val useCaseCoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    /**
     * A coroutine scope for operations that should not block the main thread,
     * typically used for I/O operations like network requests or database transactions.
     */
    override val nonBlockingCoroutineScope = CoroutineScope(Dispatchers.IO)

    /**
     * A flow of states representing the current state of the component.
     * This state is observable and can be collected from the UI to update the interface accordingly.
     */
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<STATE> = _state

    /**
     * A flow of effects, which are one-time events meant to be handled by the UI (e.g., navigation actions, toast messages).
     */
    private val _effect = Channel<EFFECT>(capacity = BUFFERED)
    val effect: Flow<EFFECT> = _effect.receiveAsFlow()

    /**
     * Updates the current state of the component. This function should be used to modify the state in a thread-safe manner.
     *
     * @param transform A lambda function that takes the current state and returns a transformed state.
     */
    protected fun updateState(transform: STATE.() -> STATE) {
        _state.update { it.transform() }
    }

    /**
     * Retrives the current state of the component.
     */
    protected fun getState(): STATE = _state.value

    /**
     * Sends an effect to be handled by the UI or an external observer. This method ensures that effects are dispatched safely and can be used to communicate with the UI layer.
     *
     * @param effect The effect to be emitted.
     */
    protected fun sendEffect(effect: EFFECT) {
        _effect.trySend(effect)
    }

    /**
     * Cleans up resources when the component is destroyed, particularly cancelling all coroutines launched by this component's use case coroutine scope.
     */
    override fun onDestroy() {
        useCaseCoroutineScope.cancel()
    }
}
