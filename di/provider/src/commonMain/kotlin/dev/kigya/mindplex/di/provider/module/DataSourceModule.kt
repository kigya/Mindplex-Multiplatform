package dev.kigya.mindplex.di.provider.module

import dev.jordond.connectivity.Connectivity
import dev.kigya.mindplex.core.data.connectivity.ConnectivityManager
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
                context = get<CoroutineDispatcher>(qualifier = named(Dispatchers.IO::class.simpleName.orEmpty())),
            ),
        )
    } onClose { connectivityManager ->
        connectivityManager?.close()
    }
}
