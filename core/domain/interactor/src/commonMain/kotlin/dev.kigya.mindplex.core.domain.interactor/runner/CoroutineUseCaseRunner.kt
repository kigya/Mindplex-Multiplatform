package dev.kigya.mindplex.core.domain.interactor.runner

import androidx.annotation.AnyThread
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

interface CoroutineUseCaseRunner {
    val useCaseCoroutineScope: CoroutineScope

    @AnyThread
    fun withUseCaseScope(block: suspend CoroutineScope.() -> Unit): Job =
        useCaseCoroutineScope.launch(block = block)
}
