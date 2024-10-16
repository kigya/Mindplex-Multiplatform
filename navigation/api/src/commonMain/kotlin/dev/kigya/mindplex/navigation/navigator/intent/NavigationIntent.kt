package dev.kigya.mindplex.navigation.navigator.intent

import dev.kigya.mindplex.navigation.navigator.route.ScreenRoute

sealed class NavigationIntent(
    open val route: ScreenRoute?,
    open val inclusive: Boolean,
) {
    data class NavigateBack(
        override val route: ScreenRoute? = null,
        override val inclusive: Boolean = false,
    ) : NavigationIntent(route, inclusive)

    data class NavigateTo(
        override val route: ScreenRoute,
        override val inclusive: Boolean = false,
        val popUpToRoute: ScreenRoute? = null,
        val isSingleTop: Boolean = false,
    ) : NavigationIntent(route, inclusive)
}
