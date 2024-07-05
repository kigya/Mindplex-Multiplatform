package dev.kigya.mindplex.core.data.connectivity

import dev.jordond.connectivity.Connectivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ConnectivityManager(
    private val connectivity: Connectivity,
    private val scope: CoroutineScope,
) {

    private val _status = MutableStateFlow<Connectivity.Status>(Connectivity.Status.Disconnected)
    val status = _status.asStateFlow()

    init {
        connectivity.start()
        scope.launch {
            connectivity.statusUpdates.collect { status ->
                _status.value = status
            }
        }
    }

    fun close() {
        scope.cancel()
        connectivity.stop()
    }
}
