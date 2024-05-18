package dev.kigya.core.domain.interactor.runner

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Provides structured coroutine scopes and utility functions for launching coroutines
 * in specific execution contexts within components or interactors.
 *
 * This interface is intended to standardize coroutine execution in different contexts,
 * providing both a use-case-specific scope and a non-blocking scope to ensure that the
 * UI remains responsive and that long-running tasks do not interfere with user interactions.
 *
 * Methods:
 * - `withUseCaseScope`: Executes a block of code within the use-case-specific coroutine scope.
 *   This scope is typically bound to the lifecycle of the component or use case and is designed
 *   to run tasks that are directly related to user interactions or critical business logic.
 *
 * - `withNonBlockingScope`: Executes a block of code within a non-blocking coroutine scope.
 *   This scope is suitable for IO operations or long-running tasks that should not block the main
 *   thread and are not critical to maintaining user interaction responsiveness.
 *
 * @see dev.kigya.mindplex.core.presentation.feature.component.BaseComponent
 */
interface CoroutineInteractorRunner {
    val useCaseCoroutineScope: CoroutineScope
    val nonBlockingCoroutineScope: CoroutineScope

    fun withUseCaseScope(block: suspend CoroutineScope.() -> Unit) {
        useCaseCoroutineScope.launch(block = block)
    }

    fun withNonBlockingScope(block: suspend CoroutineScope.() -> Unit) {
        nonBlockingCoroutineScope.launch(block = block)
    }
}
