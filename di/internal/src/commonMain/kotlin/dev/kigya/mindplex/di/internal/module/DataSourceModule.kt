package dev.kigya.mindplex.di.internal.module

import dev.jordond.connectivity.Connectivity
import dev.kigya.mindplex.core.data.connectivity.ConnectivityManager
import dev.kigya.mindplex.core.data.scout.api.ScoutNetworkClientContract
import dev.kigya.mindplex.core.data.scout.impl.ScoutNetworkClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.dsl.onClose

val dataSourceModule = module {
    single { Connectivity() } bind Connectivity::class

    factory {
        ConnectivityManager(
            connectivity = get(),
            scope = CoroutineScope(
                context = get<CoroutineDispatcher>(
                    qualifier = named(Dispatchers.IO::class.simpleName.orEmpty()),
                ),
            ),
        )
    } onClose { connectivityManager ->
        connectivityManager?.close()
    }

    factory {
        ScoutNetworkClient(
            httpClient = get(),
            jwtProvider = get(),
            dispatcher = get(qualifier = named(Dispatchers.IO::class.simpleName.orEmpty())),
        )
    } bind ScoutNetworkClientContract::class
}
