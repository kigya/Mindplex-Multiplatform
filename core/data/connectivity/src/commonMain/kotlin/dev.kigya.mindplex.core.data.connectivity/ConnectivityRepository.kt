package dev.kigya.mindplex.core.data.connectivity

import dev.kigya.mindplex.core.domain.connectivity.contract.ConnectivityRepositoryContract

class ConnectivityRepository(
    private val connectivityManager: ConnectivityManager,
) : ConnectivityRepositoryContract {

    override fun isConnected() = connectivityManager.status.value.isConnected
}
