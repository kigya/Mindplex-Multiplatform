package dev.kigya.mindplex.di.provider.module

import com.arkivanov.decompose.router.stack.StackNavigation
import dev.kigya.mindplex.navigation.navigator.Configuration
import org.koin.dsl.module

val navigationModule = module {
    single<StackNavigation<Configuration>> { StackNavigation() }
}
