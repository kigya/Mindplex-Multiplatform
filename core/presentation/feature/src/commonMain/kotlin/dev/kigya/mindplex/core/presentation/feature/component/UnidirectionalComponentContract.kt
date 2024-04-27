package dev.kigya.mindplex.core.presentation.feature.component

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/**
 * Interface defining the essential elements of a unidirectional data flow component in an application.
 * This architectural pattern facilitates handling user interactions and UI state in a predictable manner.
 *
 * This interface encapsulates state management and event handling within a component, guiding the implementation
 * of components that react to user events, manage application state, and produce side effects.
 *
 * Type Parameters:
 * @param STATE
 * Represents the type of state maintained by the component. The state is typically a data class
 * that represents all the information the UI needs to display.
 * @param EVENT
 * Represents the type of events the component can handle. These are typically user interactions
 *                or other UI events that trigger state changes.
 * @param EFFECT
 * Represents the type of side effects that can occur as a result of state changes. These are typically
 * one-time events like navigation actions, showing toasts, or other alerts that should be handled by the UI.
 */
interface UnidirectionalComponentContract<STATE, EVENT, EFFECT> {
    val state: StateFlow<STATE>
    val effect: Flow<EFFECT>

    fun handleEvent(event: EVENT)
}
