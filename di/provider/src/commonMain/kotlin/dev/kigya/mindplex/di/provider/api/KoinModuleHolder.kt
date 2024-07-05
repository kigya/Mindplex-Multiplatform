package dev.kigya.mindplex.di.provider.api

import dev.kigya.mindplex.di.provider.module.dataSourceModule
import dev.kigya.mindplex.di.provider.module.dataStoreModule
import dev.kigya.mindplex.di.provider.module.dispatcherModule
import dev.kigya.mindplex.di.provider.module.navigationModule
import dev.kigya.mindplex.di.provider.module.repositoryModule
import dev.kigya.mindplex.di.provider.module.useCaseModule
import dev.kigya.mindplex.di.provider.module.viewModelModule
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
    )
}
