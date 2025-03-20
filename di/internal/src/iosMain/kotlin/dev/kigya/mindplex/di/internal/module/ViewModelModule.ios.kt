package dev.kigya.mindplex.di.internal.module

import dev.kigya.mindplex.core.presentation.feature.host.AppActionsHostViewModel
import dev.kigya.mindplex.feature.game.presentation.ui.GameScreenViewModel
import dev.kigya.mindplex.feature.home.presentation.ui.HomeScreenViewModel
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.LeaderboardScreenViewModel
import dev.kigya.mindplex.feature.login.presentation.ui.LoginScreenViewModel
import dev.kigya.mindplex.feature.onboarding.presentation.ui.OnboardingScreenViewModel
import dev.kigya.mindplex.feature.profile.presentation.ui.ProfileScreenViewModel
import dev.kigya.mindplex.feature.splash.presentation.ui.SplashScreenViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val viewModelModule = module {
    singleOf(::AppActionsHostViewModel)
    singleOf(::SplashScreenViewModel)
    singleOf(::OnboardingScreenViewModel)
    singleOf(::HomeScreenViewModel)
    singleOf(::LoginScreenViewModel)
    singleOf(::LeaderboardScreenViewModel)
    singleOf(::ProfileScreenViewModel)
    singleOf(::GameScreenViewModel)
}
