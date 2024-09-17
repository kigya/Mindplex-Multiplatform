package dev.kigya.mindplex.navigation.navigator.intent

sealed class NavigationIntent(
    open val route: String?,
    open val inclusive: Boolean,
) {
    data class NavigateBack(
        override val route: String? = null,
        override val inclusive: Boolean = false,
    ) : NavigationIntent(route, inclusive)

    data class NavigateTo(
        override val route: String,
        override val inclusive: Boolean = false,
        val popUpToRoute: String? = null,
        val isSingleTop: Boolean = false,
    ) : NavigationIntent(route, inclusive)
}
