package dev.kigya.mindplex.core.domain.interactor.runner

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

interface CoroutineUseCaseRunner {
    val useCaseCoroutineScope: CoroutineScope

    fun withUseCaseScope(block: suspend CoroutineScope.() -> Unit) {
        useCaseCoroutineScope.launch(block = block)
    }
}
