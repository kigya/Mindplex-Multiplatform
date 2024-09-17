package dev.kigya.mindplex.core.domain.interactor.runner

import androidx.annotation.AnyThread
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

interface CoroutineUseCaseRunner {
    val useCaseCoroutineScope: CoroutineScope

    @AnyThread
    fun withUseCaseScope(block: suspend CoroutineScope.() -> Unit) {
        useCaseCoroutineScope.launch(block = block)
    }
}
