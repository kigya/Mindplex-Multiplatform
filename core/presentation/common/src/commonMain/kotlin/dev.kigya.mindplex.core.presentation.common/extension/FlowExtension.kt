@file:Suppress("MagicNumber")

package dev.kigya.mindplex.core.presentation.common.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@Composable
expect fun <T> StateFlow<T>.collectAsPlatformState(
    context: CoroutineContext = EmptyCoroutineContext,
): State<T>

inline fun <T> MutableStateFlow<T>.asStateFlowWithStartAction(
    scope: CoroutineScope,
    crossinline startAction: () -> Unit,
): StateFlow<T> = onStart {
    scope.launch {
        startAction()
    }
}.stateIn(
    scope = scope,
    started = SharingStarted.WhileSubscribed(5000L),
    initialValue = value,
)
