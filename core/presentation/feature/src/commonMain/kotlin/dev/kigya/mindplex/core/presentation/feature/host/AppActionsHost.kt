package dev.kigya.mindplex.core.presentation.feature.host

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.PredictiveBackHandler
import androidx.navigation.NavHostController
import dev.kigya.mindplex.core.presentation.common.util.koinViewModel
import dev.kigya.mindplex.core.presentation.feature.contract.ScreenHostContract
import dev.kigya.mindplex.core.presentation.feature.effect.use
import dev.kigya.mindplex.core.presentation.feature.host.theme.HostTheme
import dev.kigya.mindplex.core.presentation.uikit.AnimatedNavigationBar
import dev.kigya.mindplex.core.presentation.uikit.MindplexIconButton
import dev.kigya.mindplex.core.util.contract.enforceNonNullSmartCast
import dev.kigya.mindplex.navigation.navigator.intent.NavigationIntent
import dev.kigya.mindplex.navigation.navigator.navigator.NavigationExitHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import mindplex_multiplatform.core.presentation.feature.generated.resources.Res
import mindplex_multiplatform.core.presentation.feature.generated.resources.ic_home_active
import mindplex_multiplatform.core.presentation.feature.generated.resources.ic_home_inactive
import mindplex_multiplatform.core.presentation.feature.generated.resources.ic_leaderboard_active
import mindplex_multiplatform.core.presentation.feature.generated.resources.ic_leaderboard_inactive
import mindplex_multiplatform.core.presentation.feature.generated.resources.ic_profile_active
import mindplex_multiplatform.core.presentation.feature.generated.resources.ic_profile_inactive

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppActionsHost(
    navigationController: NavHostController,
    contract: ScreenHostContract,
    onBackPressAlphaChange: (Float) -> Unit,
) {
    val hostViewModel = koinViewModel<AppActionsHostViewModel>()

    val (state, event, _) = use(contract)

    var backGestureProgress by remember { mutableStateOf(0f) }
    val animatedAlpha by animateFloatAsState(targetValue = 1f - backGestureProgress)

    LaunchedEffect(animatedAlpha) {
        onBackPressAlphaChange(animatedAlpha)
    }

    PredictiveBackHandler(
        enabled = true,
    ) { progressFlow ->
        progressFlow.collect { backEvent ->
            backGestureProgress = backEvent.progress
        }
        backGestureProgress = 0f
        hostViewModel.onBackPressed()
    }

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
                vertical = HostTheme.dimension.dp24.value,
                horizontal = HostTheme.dimension.dp16.value,
            ),
            selectedIndex = state.activeVertical.index,
        ) {
            MindplexIconButton(
                modifier = Modifier.padding(vertical = HostTheme.dimension.dp12.value),
                resource = if (state.activeVertical == ScreenHostContract.State.Vertical.Home) {
                    Res.drawable.ic_home_active
                } else {
                    Res.drawable.ic_home_inactive
                },
            ) { event(ScreenHostContract.Event.OnHomeVerticalClicked) }

            MindplexIconButton(
                modifier = Modifier.padding(vertical = HostTheme.dimension.dp12.value),
                resource = if (state.activeVertical == ScreenHostContract.State.Vertical.Leaderboard) {
                    Res.drawable.ic_leaderboard_active
                } else {
                    Res.drawable.ic_leaderboard_inactive
                },
            ) { event(ScreenHostContract.Event.OnLeaderboardVerticalClicked) }

            MindplexIconButton(
                modifier = Modifier.padding(vertical = HostTheme.dimension.dp12.value),
                resource = if (state.activeVertical == ScreenHostContract.State.Vertical.Profile) {
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
    var shouldExit by remember { mutableStateOf(false) }

    NavigationExitHandler(shouldExit)

    LaunchedEffect(navHostController, navigationChannel) {
        navigationChannel.receiveAsFlow().collect { intent ->
            event(ScreenHostContract.Event.OnNewRouteReceived(intent.route))
            when (intent) {
                is NavigationIntent.NavigateBack -> {
                    val popped = if (intent.route != null) {
                        val safeRoute = enforceNonNullSmartCast(intent.route) ?: return@collect
                        navHostController.popBackStack(safeRoute, intent.inclusive)
                    } else {
                        navHostController.popBackStack()
                    }
                    if (!popped) {
                        shouldExit = true
                    }
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
