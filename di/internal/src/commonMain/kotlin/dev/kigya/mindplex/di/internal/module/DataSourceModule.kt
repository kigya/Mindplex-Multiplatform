package dev.kigya.mindplex.di.internal.module

import dev.jordond.connectivity.Connectivity
import dev.kigya.mindplex.core.data.connectivity.ConnectivityManager
import dev.kigya.mindplex.feature.login.data.repository.handler.JwtHandler
import dev.kigya.mindplex.feature.login.domain.contract.JwtHandlerContract
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
            scope =
            CoroutineScope(
                context = get<CoroutineDispatcher>(
                    qualifier = named(Dispatchers.IO::class.simpleName.orEmpty()),
                ),
            ),
        )
    } onClose { connectivityManager ->
        connectivityManager?.close()
    }

    single {
        JwtHandler(
            dispatcher = get(named(Dispatchers.Default::class.simpleName.orEmpty())),
        )
    } bind JwtHandlerContract::class
}
