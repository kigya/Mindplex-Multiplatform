package dev.kigya.mindplex

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.kigya.mindplex.core.presentation.feature.host.ScreenHost
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.core.util.compose.koinViewModel
import dev.kigya.mindplex.feature.home.presentation.ui.HomeScreen
import dev.kigya.mindplex.feature.home.presentation.ui.HomeScreenViewModel
import dev.kigya.mindplex.feature.login.presentation.ui.LoginScreen
import dev.kigya.mindplex.feature.login.presentation.ui.LoginScreenViewModel
import dev.kigya.mindplex.feature.onboarding.presentation.ui.OnboardingScreen
import dev.kigya.mindplex.feature.onboarding.presentation.ui.OnboardingScreenViewModel
import dev.kigya.mindplex.feature.splash.presentation.ui.SplashScreen
import dev.kigya.mindplex.feature.splash.presentation.ui.SplashScreenViewModel
import dev.kigya.mindplex.navigation.navigator.destination.Destination
import dev.kigya.mindplex.navigation.navigator.route.ScreenRoute
import org.koin.compose.KoinContext

@Composable
fun App() {
    MindplexTheme {
        KoinContext {
            val navigationController = rememberNavController()
            ScreenHost(navigationController)
            NavHost(
                navController = navigationController,
                startDestination = ScreenRoute.SPLASH,
            ) {
                composable(
                    route = Destination.Splash.fullRoute,
                    enterTransition = { fadeIn() },
                    exitTransition = { fadeOut() },
                ) { SplashScreen(koinViewModel<SplashScreenViewModel>()) }

                composable(
                    route = Destination.Onboarding.fullRoute,
                    enterTransition = { fadeIn() },
                    exitTransition = { fadeOut() },
                ) { OnboardingScreen(koinViewModel<OnboardingScreenViewModel>()) }

                composable(
                    route = Destination.Login.fullRoute,
                    enterTransition = { fadeIn() },
                    exitTransition = { fadeOut() },
                ) { LoginScreen(koinViewModel<LoginScreenViewModel>()) }

                composable(
                    route = Destination.Home.fullRoute,
                    enterTransition = { fadeIn() },
                    exitTransition = { fadeOut() },
                ) { HomeScreen(koinViewModel<HomeScreenViewModel>()) }
            }
        }
    }
}
