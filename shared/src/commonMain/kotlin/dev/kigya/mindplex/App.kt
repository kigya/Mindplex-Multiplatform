package dev.kigya.mindplex

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import dev.kigya.mindplex.core.domain.profile.contract.ThemePreferencesRepositoryContract
import dev.kigya.mindplex.core.presentation.common.util.SystemBarsColor
import dev.kigya.mindplex.core.presentation.common.util.koinViewModel
import dev.kigya.mindplex.core.presentation.feature.host.AppActionsHost
import dev.kigya.mindplex.core.presentation.feature.host.AppActionsHostViewModel
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.core.presentation.theme.window.LocalNavigationBarPaddings
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
import kotlinx.coroutines.flow.distinctUntilChanged
import org.koin.compose.KoinContext
import org.koin.compose.currentKoinScope
import org.koin.core.parameter.parametersOf
import kotlin.reflect.KType
import kotlin.reflect.typeOf

@Composable
fun App() {
    MindplexTheme(isDark = rememberIsDarkTheme()) {
        KoinContext {
            val navigationController = rememberNavController()

            val imageLoader = rememberCoilImageLoader()
            setSingletonImageLoaderFactory { imageLoader }

            var predictiveBackAlpha by remember { mutableFloatStateOf(1f) }
            var bottomBarHeight by remember { mutableStateOf(0.dp) }
            var isShowSplash by remember { mutableStateOf(true) }
            val viewModel = koinViewModel<AppActionsHostViewModel>()
            val startDestination by viewModel.startDestination.collectAsState()

            CompositionLocalProvider(LocalNavigationBarPaddings provides PaddingValues(bottom = bottomBarHeight)) {
                Box(
                    modifier = Modifier.alpha(predictiveBackAlpha),
                    contentAlignment = Alignment.BottomCenter,
                ) {
                    NavHost(
                        navController = navigationController,
                        startDestination = startDestination ?: ScreenRoute.Onboarding,
                    ) {
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

                    AppActionsHost(
                        navigationController = navigationController,
                        contract = koinViewModel<AppActionsHostViewModel>(),
                        onBackPressAlphaChange = { predictiveBackAlpha = it },
                        onBottomBarHeightMeasured = { height -> bottomBarHeight = height },
                    )
                    if (isShowSplash) {
                        SplashScreen(
                            contract = koinViewModel<SplashScreenViewModel>(),
                            onComplete = { isShowSplash = false },
                        )
                    }
                }
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
    return remember {
        koinScope.getKoin().get<ImageLoader> { parametersOf(context) }
    }
}

@Composable
private fun rememberIsDarkTheme(): Boolean {
    val koinScope = currentKoinScope()
    val isDarkTheme by koinScope
        .getKoin()
        .get<ThemePreferencesRepositoryContract>()
        .isInDarkTheme
        .distinctUntilChanged()
        .collectAsStateWithLifecycle(false)
    return remember(isDarkTheme) { isDarkTheme }
}
