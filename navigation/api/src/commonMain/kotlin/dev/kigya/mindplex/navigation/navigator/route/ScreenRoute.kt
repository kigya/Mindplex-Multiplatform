package dev.kigya.mindplex.navigation.navigator.route

import kotlinx.serialization.Serializable

sealed interface ScreenRoute {
    @Serializable
    data object Splash : ScreenRoute

    @Serializable
    data object Onboarding : ScreenRoute

    @Serializable
    data object Login : ScreenRoute

    @Serializable
    data object Home : ScreenRoute

    @Serializable
    data object Leaderboard : ScreenRoute

    @Serializable
    data object Profile : ScreenRoute

    @Serializable
    data object Game : ScreenRoute
}
