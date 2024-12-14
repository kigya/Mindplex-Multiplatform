package dev.kigya.mindplex.core.presentation.feature.host

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dev.kigya.mindplex.core.presentation.common.util.koinViewModel
import dev.kigya.mindplex.core.presentation.component.AnimatedNavigationBar
import dev.kigya.mindplex.core.presentation.component.MindplexIconButton
import dev.kigya.mindplex.core.presentation.feature.contract.ScreenHostContract
import dev.kigya.mindplex.core.presentation.feature.effect.use
import dev.kigya.mindplex.core.presentation.feature.host.theme.HostTheme
import dev.kigya.mindplex.core.util.contract.enforceNonNullSmartCast
import dev.kigya.mindplex.navigation.navigator.intent.NavigationIntent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import mindplex_multiplatform.core.presentation.feature.generated.resources.Res
import mindplex_multiplatform.core.presentation.feature.generated.resources.ic_home_active
import mindplex_multiplatform.core.presentation.feature.generated.resources.ic_home_inactive
import mindplex_multiplatform.core.presentation.feature.generated.resources.ic_leaderboard_active
import mindplex_multiplatform.core.presentation.feature.generated.resources.ic_leaderboard_inactive
import mindplex_multiplatform.core.presentation.feature.generated.resources.ic_profile_active
import mindplex_multiplatform.core.presentation.feature.generated.resources.ic_profile_inactive

@Composable
fun ScreenHost(
    navigationController: NavHostController,
    contract: ScreenHostContract,
) {
    val hostViewModel = koinViewModel<ScreenHostViewModel>()

    val (state, event, _) = use(contract)

    NavigationEffects(
        navigationChannel = hostViewModel.navigationChannel,
        navHostController = navigationController,
        event = event,
    )

    AnimatedVisibility(
        visible = state.shouldDisplayNavigationBar,
        enter = fadeIn(animationSpec = tween()),
        exit = fadeOut(animationSpec = tween()),
    ) {
        AnimatedNavigationBar(
            modifier = Modifier.padding(
                vertical = HostTheme.dimension.dp16,
                horizontal = HostTheme.dimension.dp16,
            ),
            selectedIndex = state.activeVertical.index,
        ) {
            MindplexIconButton(
                modifier = Modifier.padding(vertical = HostTheme.dimension.dp12),
                drawableResource = if (state.activeVertical == ScreenHostContract.State.Vertical.Home) {
                    Res.drawable.ic_home_active
                } else {
                    Res.drawable.ic_home_inactive
                },
            ) { event(ScreenHostContract.Event.OnHomeVerticalClicked) }

            MindplexIconButton(
                modifier = Modifier.padding(vertical = HostTheme.dimension.dp12),
                drawableResource = if (state.activeVertical == ScreenHostContract.State.Vertical.Leaderboard) {
                    Res.drawable.ic_leaderboard_active
                } else {
                    Res.drawable.ic_leaderboard_inactive
                },
            ) { event(ScreenHostContract.Event.OnLeaderboardVerticalClicked) }

            MindplexIconButton(
                modifier = Modifier.padding(vertical = HostTheme.dimension.dp12),
                drawableResource = if (state.activeVertical == ScreenHostContract.State.Vertical.Profile) {
                    Res.drawable.ic_profile_active
                } else {
                    Res.drawable.ic_profile_inactive
                },
            ) { event(ScreenHostContract.Event.OnProfileVerticalClicked) }
        }
    }
}

@Composable
private fun NavigationEffects(
    navigationChannel: Channel<NavigationIntent>,
    navHostController: NavHostController,
    event: (ScreenHostContract.Event) -> Unit,
) {
    LaunchedEffect(navHostController, navigationChannel) {
        navigationChannel.receiveAsFlow().collect { intent ->
            event(ScreenHostContract.Event.OnNewRouteReceived(intent.route))
            when (intent) {
                is NavigationIntent.NavigateBack -> if (intent.route != null) {
                    val safeRoute = enforceNonNullSmartCast(intent.route) ?: return@collect
                    navHostController.popBackStack(safeRoute, intent.inclusive)
                } else {
                    navHostController.popBackStack()
                }

                is NavigationIntent.NavigateTo -> navHostController.navigate(intent.route) {
                    launchSingleTop = intent.isSingleTop
                    intent.popUpToRoute?.let { popUpToRoute ->
                        popUpTo(popUpToRoute) { inclusive = intent.inclusive }
                    }
                }
            }
        }
    }
}
