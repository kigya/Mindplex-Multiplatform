package dev.kigya.mindplex.navigation.internal

import dev.kigya.mindplex.navigation.navigator.intent.NavigationIntent
import dev.kigya.mindplex.navigation.navigator.navigator.MindplexNavigatorContract
import dev.kigya.mindplex.navigation.navigator.route.ScreenRoute
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel

class MindplexNavigator : MindplexNavigatorContract {
    override val navigationChannel =
        Channel<NavigationIntent>(
            capacity = Int.MAX_VALUE,
            onBufferOverflow = BufferOverflow.DROP_LATEST,
        )

    private var _routeHistory: List<ScreenRoute> = emptyList()
    override val routeHistory: List<ScreenRoute> get() = _routeHistory

    override suspend fun navigateBack(
        route: ScreenRoute?,
        inclusive: Boolean,
    ) {
        val newCurrent = popRouteHistory(route, inclusive)
        navigationChannel.send(
            NavigationIntent.NavigateBack(
                route = newCurrent,
                inclusive = inclusive,
            ),
        )
    }

    override fun tryNavigateBack(
        route: ScreenRoute?,
        inclusive: Boolean,
    ) {
        val newCurrent = popRouteHistory(route, inclusive)
        navigationChannel.trySend(
            NavigationIntent.NavigateBack(
                route = newCurrent,
                inclusive = false,
            ),
        )
    }

    override suspend fun navigateTo(
        route: ScreenRoute,
        popUpToRoute: ScreenRoute?,
        inclusive: Boolean,
        isSingleTop: Boolean,
    ) {
        pushRouteHistory(route, popUpToRoute, inclusive, isSingleTop)
        navigationChannel.send(
            NavigationIntent.NavigateTo(
                route = route,
                popUpToRoute = popUpToRoute,
                inclusive = inclusive,
                isSingleTop = isSingleTop,
            ),
        )
    }

    override fun tryNavigateTo(
        route: ScreenRoute,
        popUpToRoute: ScreenRoute?,
        inclusive: Boolean,
        isSingleTop: Boolean,
    ) {
        pushRouteHistory(route, popUpToRoute, inclusive, isSingleTop)
        navigationChannel.trySend(
            NavigationIntent.NavigateTo(
                route = route,
                popUpToRoute = popUpToRoute,
                inclusive = inclusive,
                isSingleTop = isSingleTop,
            ),
        )
    }

    private fun popRouteHistory(
        targetRoute: ScreenRoute?,
        inclusive: Boolean,
    ): ScreenRoute? {
        val oldHistory = _routeHistory

        val newHistory = if (targetRoute == null) {
            if (oldHistory.isNotEmpty()) oldHistory.dropLast(1) else oldHistory
        } else {
            val idx = oldHistory.indexOfLast { it == targetRoute }
            if (idx >= 0) {
                oldHistory.take(idx + if (inclusive) 0 else 1)
            } else {
                emptyList()
            }
        }

        _routeHistory = newHistory
        return newHistory.lastOrNull()
    }

    private fun pushRouteHistory(
        newRoute: ScreenRoute,
        popUpToRoute: ScreenRoute?,
        inclusive: Boolean,
        isSingleTop: Boolean,
    ) {
        var newHistory = _routeHistory

        if (isSingleTop && newHistory.lastOrNull() == newRoute) {
            return
        }

        if (popUpToRoute != null) {
            val idx = newHistory.indexOfLast { it == popUpToRoute }
            newHistory = if (idx >= 0) {
                newHistory.take(idx + if (inclusive) 0 else 1)
            } else {
                emptyList()
            }
        }

        newHistory = newHistory + newRoute
        _routeHistory = newHistory
    }
}
