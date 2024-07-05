package dev.kigya.mindplex.core.presentation.feature

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface UnidirectionalViewModelContract<STATE, EVENT, EFFECT> {
    val state: StateFlow<STATE>
    val effect: Flow<EFFECT>

    fun handleEvent(event: EVENT)
}
