package dev.kigya.mindplex.navigation.navigator.navigator

import dev.kigya.mindplex.navigation.navigator.intent.NavigationIntent
import dev.kigya.mindplex.navigation.navigator.route.ScreenRoute
import kotlinx.coroutines.channels.Channel

interface MindplexNavigatorContract {
    val navigationChannel: Channel<NavigationIntent>
    val routeHistory: List<ScreenRoute>

    suspend fun navigateBack(
        route: ScreenRoute? = null,
        inclusive: Boolean = false,
    )

    fun tryNavigateBack(
        route: ScreenRoute? = null,
        inclusive: Boolean = false,
    )

    suspend fun navigateTo(
        route: ScreenRoute,
        popUpToRoute: ScreenRoute? = null,
        inclusive: Boolean = false,
        isSingleTop: Boolean = false,
    )

    fun tryNavigateTo(
        route: ScreenRoute,
        popUpToRoute: ScreenRoute? = null,
        inclusive: Boolean = false,
        isSingleTop: Boolean = false,
    )

    suspend fun preloadAndInitializeScreen(route: ScreenRoute)
}
