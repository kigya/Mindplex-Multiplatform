package dev.kigya.mindplex.di.provider.module

import dev.kigya.mindplex.di.provider.AndroidDataStore
import org.koin.dsl.module

actual val dataStoreModule = module { single { AndroidDataStore(context = get()).getDataStore() } }
