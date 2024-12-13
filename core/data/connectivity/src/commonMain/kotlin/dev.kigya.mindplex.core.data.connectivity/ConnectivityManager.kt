package dev.kigya.mindplex.core.data.connectivity

import dev.jordond.connectivity.Connectivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class ConnectivityManager(
    private val connectivity: Connectivity,
    private val scope: CoroutineScope,
) {

    val status = connectivity.statusUpdates
        .onStart {
            connectivity.start()
            emit(Connectivity.Status.Disconnected)
        }
        .stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = Connectivity.Status.Disconnected,
        )

    fun close() {
        scope.cancel()
        connectivity.stop()
    }
}
