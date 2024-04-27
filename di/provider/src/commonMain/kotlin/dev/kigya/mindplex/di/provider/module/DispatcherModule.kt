package dev.kigya.mindplex.di.provider.module

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainCoroutineDispatcher
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dispatcherModule = module {
    single<CoroutineDispatcher>(
        qualifier = named(Dispatchers.IO::class.simpleName.orEmpty()),
    ) { Dispatchers.IO }

    single<MainCoroutineDispatcher>(
        qualifier = named(Dispatchers.Main::class.simpleName.orEmpty()),
    ) { Dispatchers.Main }

    single<CoroutineDispatcher>(
        qualifier = named(Dispatchers.Default::class.simpleName.orEmpty()),
    ) { Dispatchers.Default }
}
