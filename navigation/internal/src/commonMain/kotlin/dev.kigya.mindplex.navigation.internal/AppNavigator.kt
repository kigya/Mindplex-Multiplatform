package dev.kigya.mindplex.navigation.internal

import dev.kigya.mindplex.navigation.navigator.intent.NavigationIntent
import dev.kigya.mindplex.navigation.navigator.navigator.AppNavigatorContract
import dev.kigya.mindplex.navigation.navigator.route.ScreenRoute
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel

class AppNavigator : AppNavigatorContract {
    override val navigationChannel =
        Channel<NavigationIntent>(
            capacity = Int.MAX_VALUE,
            onBufferOverflow = BufferOverflow.DROP_LATEST,
        )

    override suspend fun navigateBack(
        route: ScreenRoute?,
        inclusive: Boolean,
    ) {
        navigationChannel.send(
            NavigationIntent.NavigateBack(
                route = route,
                inclusive = inclusive,
            ),
        )
    }

    override fun tryNavigateBack(
        route: ScreenRoute?,
        inclusive: Boolean,
    ) {
        navigationChannel.trySend(
            NavigationIntent.NavigateBack(
                route = route,
                inclusive = inclusive,
            ),
        )
    }

    override suspend fun navigateTo(
        route: ScreenRoute,
        popUpToRoute: ScreenRoute?,
        inclusive: Boolean,
        isSingleTop: Boolean,
    ) {
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
        navigationChannel.trySend(
            NavigationIntent.NavigateTo(
                route = route,
                popUpToRoute = popUpToRoute,
                inclusive = inclusive,
                isSingleTop = isSingleTop,
            ),
        )
    }
}
