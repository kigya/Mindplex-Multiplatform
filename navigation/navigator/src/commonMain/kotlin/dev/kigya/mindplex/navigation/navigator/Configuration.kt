package dev.kigya.mindplex.navigation.navigator

import kotlinx.serialization.Serializable

@Serializable
sealed class Configuration {
    @Serializable
    data object SplashScreen : Configuration()

    @Serializable
    data object OnboardingScreen : Configuration()

    @Serializable
    data object HomeScreen : Configuration()
}
