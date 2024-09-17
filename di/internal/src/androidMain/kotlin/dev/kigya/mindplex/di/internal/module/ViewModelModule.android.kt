package dev.kigya.mindplex.di.internal.module

import dev.kigya.mindplex.core.presentation.feature.host.ScreenHostViewModel
import dev.kigya.mindplex.feature.home.presentation.ui.HomeScreenViewModel
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.LeaderboardScreenViewModel
import dev.kigya.mindplex.feature.login.presentation.ui.LoginScreenViewModel
import dev.kigya.mindplex.feature.onboarding.presentation.ui.OnboardingScreenViewModel
import dev.kigya.mindplex.feature.profile.presentation.ui.ProfileScreenViewModel
import dev.kigya.mindplex.feature.splash.presentation.ui.SplashScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

actual val viewModelModule = module {
    viewModelOf(::ScreenHostViewModel)
    viewModelOf(::SplashScreenViewModel)
    viewModelOf(::OnboardingScreenViewModel)
    viewModelOf(::HomeScreenViewModel)
    viewModelOf(::LoginScreenViewModel)
    viewModelOf(::LeaderboardScreenViewModel)
    viewModelOf(::ProfileScreenViewModel)
}
