package dev.kigya.mindplex.di.internal.module

import dev.kigya.mindplex.navigation.internal.MindplexNavigator
import dev.kigya.mindplex.navigation.navigator.navigator.MindplexNavigatorContract
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val navigationModule = module {
    singleOf(::MindplexNavigator) bind MindplexNavigatorContract::class
}
