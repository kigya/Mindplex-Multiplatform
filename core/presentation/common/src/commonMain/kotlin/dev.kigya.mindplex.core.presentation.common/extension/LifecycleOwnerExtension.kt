package dev.kigya.mindplex.core.presentation.common.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import dev.kigya.mindplex.core.util.extension.Lambda

@Composable
fun LifecycleOwner.listenToLifecycleEvents(
    onCreate: () -> Unit = Lambda.empty(),
    onStart: () -> Unit = Lambda.empty(),
    onResume: () -> Unit = Lambda.empty(),
    onPause: () -> Unit = Lambda.empty(),
    onStop: () -> Unit = Lambda.empty(),
    onDestroy: () -> Unit = Lambda.empty(),
) {
    DisposableEffect(Unit) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> onCreate()
                Lifecycle.Event.ON_START -> onStart()
                Lifecycle.Event.ON_RESUME -> onResume()
                Lifecycle.Event.ON_PAUSE -> onPause()
                Lifecycle.Event.ON_STOP -> onStop()
                Lifecycle.Event.ON_DESTROY -> onDestroy()
                else -> Unit
            }
        }

        lifecycle.addObserver(lifecycleObserver)

        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }
}
