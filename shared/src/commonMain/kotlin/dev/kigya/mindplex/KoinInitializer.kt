package dev.kigya.mindplex

import dev.kigya.mindplex.di.provider.api.KoinModuleHolder
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Initializes the Koin modules. This method should be invoked from platform module,
 * that does not require passing additional Koin modules, e.g. KoinHelperKt.doInitKoin()
 */
fun initKoin() = initKoin(module { })

/**
 * Initializes the Koin modules.
 *
 * @param appModule the app module to be included
 */
@Suppress("SpreadOperator")
fun initKoin(appModule: Module = module {}) {
    startKoin {
        modules(appModule, *KoinModuleHolder.getAppModules())
    }
}
