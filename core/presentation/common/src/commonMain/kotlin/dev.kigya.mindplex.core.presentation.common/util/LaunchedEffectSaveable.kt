package dev.kigya.mindplex.core.presentation.common.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope

/**
 * Executes a suspendable block only once during the composition lifecycle, even across configuration changes.
 * Intended for use with immutable keys to preserve the "already executed" state through recompositions.
 *
 * @param key An immutable key to associate the launched effect.
 * It ensures the effect is not restarted during recompositions.
 * @param block The suspendable block of code to be executed.
 */
@Composable
fun LaunchedEffectSaveable(key: Any?, block: suspend CoroutineScope.() -> Unit) {
    var hasAlreadyExecuted by rememberSaveable { mutableStateOf(false) }

    if (!hasAlreadyExecuted) {
        LaunchedEffect(
            key1 = key,
            block = block,
        )
        hasAlreadyExecuted = true
    }
}
