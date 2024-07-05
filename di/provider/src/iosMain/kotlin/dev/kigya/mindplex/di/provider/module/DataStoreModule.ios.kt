package dev.kigya.mindplex.di.provider.module

import dev.kigya.mindplex.di.provider.IosDataStore
import org.koin.dsl.module

actual val dataStoreModule = module { single { IosDataStore().getDataStore() } }
