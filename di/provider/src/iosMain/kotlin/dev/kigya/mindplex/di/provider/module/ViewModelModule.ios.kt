package dev.kigya.mindplex.di.provider.module

import dev.kigya.mindplex.core.presentation.feature.host.ScreenHostViewModel
import dev.kigya.mindplex.feature.home.presentation.ui.HomeScreenViewModel
import dev.kigya.mindplex.feature.login.presentation.ui.LoginScreenViewModel
import dev.kigya.mindplex.feature.onboarding.presentation.ui.OnboardingScreenViewModel
import dev.kigya.mindplex.feature.splash.presentation.ui.SplashScreenViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val viewModelModule = module {
    singleOf(::ScreenHostViewModel)
    singleOf(::SplashScreenViewModel)
    singleOf(::OnboardingScreenViewModel)
    singleOf(::HomeScreenViewModel)
    singleOf(::LoginScreenViewModel)
}
