package dev.kigya.mindplex.di.internal.module

import dev.kigya.mindplex.di.internal.AndroidDataStore
import org.koin.dsl.module

actual val dataStoreModule = module {
    single { AndroidDataStore(context = get()).getDataStore() }
}
