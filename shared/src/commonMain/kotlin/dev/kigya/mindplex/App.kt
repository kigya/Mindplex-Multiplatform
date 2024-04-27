package dev.kigya.mindplex

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.feature.home.presentation.ui.HomeScreen
import dev.kigya.mindplex.feature.onboarding.presentation.ui.OnboardingScreen
import dev.kigya.mindplex.feature.splash.presentation.ui.SplashScreen
import dev.kigya.mindplex.navigation.mediator.RootComponent


@Composable
fun App(root: RootComponent) {
    val childStack by root.childStack.subscribeAsState()
    MindplexTheme {
        Children(
            stack = childStack,
            animation = stackAnimation(fade())
        ) { child ->
            when (val instance = child.instance) {
                is RootComponent.Child.SplashScreen -> SplashScreen(instance.component)
                is RootComponent.Child.OnboardingScreen -> OnboardingScreen(instance.component)
                is RootComponent.Child.HomeScreen -> HomeScreen(instance.component)
            }
        }
    }
}
