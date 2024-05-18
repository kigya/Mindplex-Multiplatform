package dev.kigya.mindplex.di.core.root

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import org.koin.core.KoinApplication
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.module.Module
import org.koin.core.scope.Scope
import org.koin.dsl.koinApplication

/**
 * Manages a Koin Application context for a component, ensuring that dependency injection
 * is properly set up and torn down in conjunction with the component's lifecycle.
 *
 * This class specifically handles creating and maintaining a single KoinApplication
 * instance which provides a Koin scope used for dependency injection. The Koin application
 * is configured with provided modules, ensuring all dependencies are available within the scope.
 *
 * Usage:
 * This class is typically used within the setup of a component that requires dependency injection,
 * such as a RootComponent in a multiplatform application, to manage the lifetime of injected dependencies
 * in alignment with the component's lifecycle.
 */
class ComponentKoinContext : InstanceKeeper.Instance {
    private var koinApp: KoinApplication? = null

    @Suppress("SpreadOperator")
    @OptIn(KoinInternalApi::class)
    fun getOrCreateKoinScope(modules: Array<Module>): Scope {
        if (koinApp == null) {
            koinApp = koinApplication { modules(*modules) }
        }
        return requireNotNull(koinApp).koin.scopeRegistry.rootScope
    }

    override fun onDestroy() {
        koinApp?.close()
        koinApp = null
    }
}
