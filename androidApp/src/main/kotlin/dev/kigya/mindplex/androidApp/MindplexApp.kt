package dev.kigya.mindplex.androidApp

import android.app.Application
import android.content.Context
import dev.kigya.mindplex.initKoin
import org.koin.dsl.module

/**
 * `MindplexApp` extends the Android `Application` class, acting as the main entry point
 * for global application-level initialization.
 *
 * Responsibilities of `MindplexApp` include:
 * - Initializing the Koin DI container with modules that are essential for the application.
 * - Providing a global application context to the DI container to ensure that singleton
 *   services like Context are available throughout the application.
 *
 * The class defines `appModule`, a Koin module, which is crucial for injecting the Android
 * application context into various parts of the application. This module is necessary because
 * platform-specific modules cannot be defined in shared/common Koin configurations, thus it
 * needs to be explicitly defined and managed at the application level.
 *
 * Usage:
 * - The `appModule` is included during the initialization of the `RootComponent` which requires
 *   an array of modules for its setup. This ensures that all necessary dependencies are provided
 *   when constructing application components.
 *
 * Example of extending the DI setup:
 * ```kotlin
 * fun getAppModules(appModule: Module = module {}) = arrayOf(
 *     appModule,
 *     dataStoreModule,
 *     repositoryModule,
 *     navigationModule,
 *     useCaseModule,
 *     ...
 * )
 * ```
 *
 * The `RootComponent` uses `getAppModules(appModule)` to initialize its scope with necessary dependencies.
 */
internal class MindplexApp : Application() {
    val appModule = module { single<Context> { this@MindplexApp } }

    override fun onCreate() {
        super.onCreate()
        initKoin(appModule = appModule)
    }
}
