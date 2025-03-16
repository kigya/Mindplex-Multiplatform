package dev.kigya.mindplex

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import coil3.ImageLoader
import coil3.compose.LocalPlatformContext
import coil3.compose.setSingletonImageLoaderFactory
import dev.kigya.mindplex.core.presentation.common.util.SystemBarsColor
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
import dev.kigya.mindplex.navigation.navigator.type.enumNavTypeEntry
import dev.kigya.mindplex.navigation.navigator.type.nullableEnumNavType
import org.koin.compose.KoinContext
import org.koin.compose.currentKoinScope
import org.koin.core.parameter.parametersOf
import kotlin.reflect.KType
import kotlin.reflect.typeOf

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
                        SystemBarsColor(SystemBarsColor.LIGHT)
                        SplashScreen(koinViewModel<SplashScreenViewModel>())
                    }

                    animatedComposable<ScreenRoute.Onboarding> {
                        SystemBarsColor(SystemBarsColor.LIGHT)
                        OnboardingScreen(koinViewModel<OnboardingScreenViewModel>())
                    }

                    animatedComposable<ScreenRoute.Login> {
                        SystemBarsColor(SystemBarsColor.AUTO)
                        LoginScreen(koinViewModel<LoginScreenViewModel>())
                    }

                    animatedComposable<ScreenRoute.Home> {
                        SystemBarsColor(SystemBarsColor.AUTO)
                        HomeScreen(koinViewModel<HomeScreenViewModel>())
                    }

                    animatedComposable<ScreenRoute.Leaderboard> {
                        SystemBarsColor(SystemBarsColor.AUTO)
                        LeaderboardScreen(koinViewModel<LeaderboardScreenViewModel>())
                    }

                    animatedComposable<ScreenRoute.Profile> {
                        SystemBarsColor(SystemBarsColor.AUTO)
                        ProfileScreen(koinViewModel<ProfileScreenViewModel>())
                    }

                    animatedComposable<ScreenRoute.Game>(
                        typeMap = mapOf(
                            enumNavTypeEntry(ScreenRoute.Game.TypePresentationModel::valueOf),
                            enumNavTypeEntry(ScreenRoute.Game.DifficultyPresentationModel::valueOf),
                            enumNavTypeEntry(ScreenRoute.Game.CategoryPresentationModel::valueOf),
                            typeOf<ScreenRoute.Game.CategoryPresentationModel?>() to
                                nullableEnumNavType(ScreenRoute.Game.CategoryPresentationModel::valueOf),
                            typeOf<ScreenRoute.Game.DifficultyPresentationModel?>() to
                                nullableEnumNavType(ScreenRoute.Game.DifficultyPresentationModel::valueOf),
                        ),
                    ) { backStackEntry ->
                        SystemBarsColor(SystemBarsColor.AUTO)

                        val arguments = backStackEntry.toRoute<ScreenRoute.Game>()
                        GameScreen(
                            contract = koinViewModel<GameScreenViewModel>(),
                            type = arguments.type,
                            category = arguments.category,
                            difficulty = arguments.difficulty,
                        )
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
    typeMap: Map<KType, NavType<*>> = emptyMap(),
    crossinline screen: @Composable (NavBackStackEntry) -> Unit,
) = composable<T>(
    enterTransition = { fadeIn() },
    exitTransition = { fadeOut() },
    typeMap = typeMap,
) { screen(it) }

@Composable
private fun rememberCoilImageLoader(): ImageLoader {
    val koinScope = currentKoinScope()
    val context = LocalPlatformContext.current
    return remember { koinScope.getKoin().get<ImageLoader> { parametersOf(context) } }
}
