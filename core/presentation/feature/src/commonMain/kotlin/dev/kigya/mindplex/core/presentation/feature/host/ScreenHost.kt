package dev.kigya.mindplex.core.presentation.feature.host

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import dev.kigya.mindplex.core.util.compose.koinViewModel
import dev.kigya.mindplex.core.util.contract.enforceNonNullSmartCast
import dev.kigya.mindplex.navigation.navigator.intent.NavigationIntent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun ScreenHost(navigationController: NavHostController) {
    val hostViewModel = koinViewModel<ScreenHostViewModel>()

    NavigationEffects(
        navigationChannel = hostViewModel.navigationChannel,
        navHostController = navigationController,
    )
}

@Composable
private fun NavigationEffects(
    navigationChannel: Channel<NavigationIntent>,
    navHostController: NavHostController,
) {
    LaunchedEffect(navHostController, navigationChannel) {
        navigationChannel.receiveAsFlow().collect { intent ->
            when (intent) {
                is NavigationIntent.NavigateBack -> {
                    if (intent.route != null) {
                        val safeRoute = enforceNonNullSmartCast(intent.route) ?: return@collect
                        navHostController.popBackStack(safeRoute, intent.inclusive)
                    } else {
                        navHostController.popBackStack()
                    }
                }

                is NavigationIntent.NavigateTo -> {
                    navHostController.navigate(intent.route) {
                        launchSingleTop = intent.isSingleTop
                        intent.popUpToRoute?.let { popUpToRoute ->
                            popUpTo(popUpToRoute) { inclusive = intent.inclusive }
                        }
                    }
                }
            }
        }
    }
}
