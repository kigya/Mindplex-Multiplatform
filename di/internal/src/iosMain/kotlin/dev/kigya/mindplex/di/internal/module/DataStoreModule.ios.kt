package dev.kigya.mindplex.di.internal.module

import dev.kigya.mindplex.di.internal.IosDataStore
import org.koin.dsl.module

actual val dataStoreModule = module { single { IosDataStore().getDataStore() } }
