package dev.kigya.mindplex.navigation.mediator

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.essenty.instancekeeper.getOrCreate
import dev.kigya.mindplex.di.core.root.ComponentKoinContext
import dev.kigya.mindplex.di.provider.api.KoinModuleHolder
import dev.kigya.mindplex.feature.home.presentation.component.HomeComponent
import dev.kigya.mindplex.feature.onboarding.presentation.component.OnboardingComponent
import dev.kigya.mindplex.feature.splash.presentation.component.SplashComponent
import dev.kigya.mindplex.navigation.navigator.Configuration
import org.koin.core.module.Module
import org.koin.dsl.module

class RootComponent(
    componentContext: ComponentContext,
    appModule: Module = module {},
) : ComponentContext by componentContext {

    private val scope = instanceKeeper.getOrCreate(::ComponentKoinContext).getOrCreateKoinScope(
        KoinModuleHolder.getAppModules(appModule)
    )

    val childStack = childStack(
        source = scope.get<StackNavigation<Configuration>>(),
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.SplashScreen,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(
        config: Configuration,
        context: ComponentContext,
    ): Child = when (config) {
        Configuration.SplashScreen -> Child.SplashScreen(
            SplashComponent(
                componentContext = context,
                navigation = scope.get(),
                getIsOnboardingCompletedUseCase = scope.get()
            )
        )

        is Configuration.OnboardingScreen -> Child.OnboardingScreen(
            OnboardingComponent(
                componentContext = context,
                navigation = scope.get(),
                setOnboardingCompletedUseCase = scope.get()
            )
        )

        is Configuration.HomeScreen -> Child.HomeScreen(
            HomeComponent(
                componentContext = context,
            )
        )
    }

    sealed class Child {
        data class SplashScreen(val component: SplashComponent) : Child()
        data class OnboardingScreen(val component: OnboardingComponent) : Child()
        data class HomeScreen(val component: HomeComponent) : Child()
    }
}
