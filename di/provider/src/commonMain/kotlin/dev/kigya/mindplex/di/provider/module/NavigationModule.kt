package dev.kigya.mindplex.di.provider.module

import dev.kigya.mindplex.navigation.navigator.navigator.AppNavigator
import dev.kigya.mindplex.navigation.navigator.navigator.AppNavigatorContract
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val navigationModule = module {
    singleOf(::AppNavigator) bind AppNavigatorContract::class
}
