package dev.kigya.mindplex

import dev.kigya.mindplex.di.provider.api.KoinModuleHolder
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module


fun initKoin() = initKoin(module { })

@Suppress("SpreadOperator")
fun initKoin(appModule: Module = module {}) {
    startKoin {
        modules(appModule, *KoinModuleHolder.getAppModules())
    }
}
