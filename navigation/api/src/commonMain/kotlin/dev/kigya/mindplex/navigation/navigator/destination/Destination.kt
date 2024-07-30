package dev.kigya.mindplex.navigation.navigator.destination

import dev.kigya.mindplex.navigation.navigator.route.ScreenRoute

sealed class Destination(protected val route: String, vararg params: String) {
    val fullRoute: String = if (params.isEmpty()) {
        route
    } else {
        val builder = StringBuilder(route)
        params.forEach { builder.append("/{$it}") }
        builder.toString()
    }

    sealed class NoArgumentsDestination(route: String) : Destination(route) {
        operator fun invoke() = route
    }

    data object Splash : NoArgumentsDestination(route = ScreenRoute.SPLASH)

    data object Onboarding : NoArgumentsDestination(route = ScreenRoute.ONBOARDING)

    data object Login : NoArgumentsDestination(route = ScreenRoute.LOGIN)

    data object Home : NoArgumentsDestination(route = ScreenRoute.HOME)
}
