package dev.kigya.mindplex.core.presentation.common.util

import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlin.jvm.JvmInline

/**
 * Stable holder for unstable [Flow] value.
 */
@Stable
@JvmInline
value class StableFlow<T>(@Stable val value: Flow<T>) {
    companion object
}

fun <T> StableFlow.Companion.empty(): StableFlow<T> = StableFlow(emptyFlow())
