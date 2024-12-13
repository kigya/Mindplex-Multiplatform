package dev.kigya.mindplex

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.LocalPlatformContext
import coil3.compose.setSingletonImageLoaderFactory
import dev.kigya.mindplex.core.presentation.common.util.koinViewModel
import dev.kigya.mindplex.core.presentation.feature.host.ScreenHost
import dev.kigya.mindplex.core.presentation.feature.host.ScreenHostViewModel
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.feature.game.presentation.ui.GameScreen
import dev.kigya.mindplex.feature.game.presentation.ui.GameScreenViewModel
import dev.kigya.mindplex.feature.home.presentation.ui.HomeScreen
import dev.kigya.mindplex.feature.home.presentation.ui.HomeScreenViewModel
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.LeaderboardScreen
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.LeaderboardScreenViewModel
import dev.kigya.mindplex.feature.login.presentation.ui.LoginScreen
import dev.kigya.mindplex.feature.login.presentation.ui.LoginScreenViewModel
import dev.kigya.mindplex.feature.onboarding.presentation.ui.OnboardingScreen
import dev.kigya.mindplex.feature.onboarding.presentation.ui.OnboardingScreenViewModel
import dev.kigya.mindplex.feature.profile.presentation.ui.ProfileScreen
import dev.kigya.mindplex.feature.profile.presentation.ui.ProfileScreenViewModel
import dev.kigya.mindplex.feature.splash.presentation.ui.SplashScreen
import dev.kigya.mindplex.feature.splash.presentation.ui.SplashScreenViewModel
import dev.kigya.mindplex.navigation.navigator.route.ScreenRoute
import org.koin.compose.KoinContext
import org.koin.compose.currentKoinScope
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalCoilApi::class)
@Composable
fun App() {
    MindplexTheme {
        KoinContext {
            val navigationController = rememberNavController()

            val imageLoader = rememberCoilImageLoader()
            setSingletonImageLoaderFactory { imageLoader }

            Box(contentAlignment = Alignment.BottomCenter) {
                NavHost(
                    navController = navigationController,
                    startDestination = ScreenRoute.Splash,
                ) {
                    animatedComposable<ScreenRoute.Splash> {
                        SplashScreen(koinViewModel<SplashScreenViewModel>())
                    }

                    animatedComposable<ScreenRoute.Onboarding> {
                        OnboardingScreen(koinViewModel<OnboardingScreenViewModel>())
                    }

                    animatedComposable<ScreenRoute.Login> {
                        LoginScreen(koinViewModel<LoginScreenViewModel>())
                    }

                    animatedComposable<ScreenRoute.Home> {
                        HomeScreen(koinViewModel<HomeScreenViewModel>())
                    }

                    animatedComposable<ScreenRoute.Leaderboard> {
                        LeaderboardScreen(koinViewModel<LeaderboardScreenViewModel>())
                    }

                    animatedComposable<ScreenRoute.Profile> {
                        ProfileScreen(koinViewModel<ProfileScreenViewModel>())
                    }

                    animatedComposable<ScreenRoute.Game> {
                        GameScreen(koinViewModel<GameScreenViewModel>())
                    }
                }

                ScreenHost(
                    navigationController = navigationController,
                    contract = koinViewModel<ScreenHostViewModel>(),
                )
            }
        }
    }
}

private inline fun <reified T : Any> NavGraphBuilder.animatedComposable(
    crossinline screen: @Composable () -> Unit,
) = composable<T>(
    enterTransition = { fadeIn() },
    exitTransition = { fadeOut() },
) { screen() }

@Composable
private fun rememberCoilImageLoader(): ImageLoader {
    val koinScope = currentKoinScope()
    val context = LocalPlatformContext.current
    return remember { koinScope.getKoin().get<ImageLoader> { parametersOf(context) } }
}
