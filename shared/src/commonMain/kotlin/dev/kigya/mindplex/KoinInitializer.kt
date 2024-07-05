package dev.kigya.mindplex

import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider
import dev.kigya.mindplex.di.provider.api.KoinModuleHolder
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

fun initKoin() = initKoin(module {})

@Suppress("SpreadOperator")
fun initKoin(appModule: Module = module {}) {
    startKoin {
        createAppDependencies()
        modules(appModule, *KoinModuleHolder.getAppModules())
    }
}

private fun createAppDependencies() {
    GoogleAuthProvider.create(
        credentials = GoogleAuthCredentials(
            serverId = "415983434678-aid9os1bmjts7vrqup66au7slsacp4hu.apps.googleusercontent.com",
        ),
    )
}
