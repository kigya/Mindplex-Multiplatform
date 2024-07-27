package dev.kigya.mindplex.di.api

import dev.kigya.mindplex.di.internal.module.dataSourceModule
import dev.kigya.mindplex.di.internal.module.dataStoreModule
import dev.kigya.mindplex.di.internal.module.dispatcherModule
import dev.kigya.mindplex.di.internal.module.libsModule
import dev.kigya.mindplex.di.internal.module.navigationModule
import dev.kigya.mindplex.di.internal.module.repositoryModule
import dev.kigya.mindplex.di.internal.module.useCaseModule
import dev.kigya.mindplex.di.internal.module.viewModelModule
import org.koin.core.module.Module
import org.koin.dsl.module

object KoinModuleHolder {
    fun getAppModules(appModule: Module = module {}) = arrayOf(
        appModule,
        dataStoreModule,
        repositoryModule,
        navigationModule,
        useCaseModule,
        dispatcherModule,
        viewModelModule,
        dataSourceModule,
        libsModule,
    )
}
