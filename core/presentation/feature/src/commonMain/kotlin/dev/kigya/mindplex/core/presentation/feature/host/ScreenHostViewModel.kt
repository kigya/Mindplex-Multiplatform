package dev.kigya.mindplex.core.presentation.feature.host

import dev.kigya.mindplex.core.presentation.feature.BaseViewModel
import dev.kigya.mindplex.core.presentation.feature.contract.ScreenHostContract
import dev.kigya.mindplex.navigation.navigator.navigator.MindplexNavigatorContract
import dev.kigya.mindplex.navigation.navigator.route.ScreenRoute
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ScreenHostViewModel(
    navigatorContract: MindplexNavigatorContract,
) : BaseViewModel<ScreenHostContract.State, ScreenHostContract.Effect>(
    navigatorContract = navigatorContract,
    initialState = ScreenHostContract.State(),
),
    ScreenHostContract {

    val navigationChannel = navigatorContract.navigationChannel

    override fun handleEvent(event: ScreenHostContract.Event) = withUseCaseScope {
        event.run {
            when (this) {
                is ScreenHostContract.Event.OnHomeVerticalClicked -> handleVerticalClick(
                    ScreenHostContract.State.Vertical.Home,
                )

                is ScreenHostContract.Event.OnLeaderboardVerticalClicked -> handleVerticalClick(
                    ScreenHostContract.State.Vertical.Leaderboard,
                )

                is ScreenHostContract.Event.OnProfileVerticalClicked -> handleVerticalClick(
                    ScreenHostContract.State.Vertical.Profile,
                )

                is ScreenHostContract.Event.OnNewRouteReceived -> handleNewRouteReceive()
            }
        }
    }

    fun onBackPressed() = withUseCaseScope {
        navigatorContract.navigateBack()
    }

    private suspend fun handleVerticalClick(selectedVertical: ScreenHostContract.State.Vertical) {
        val activeVertical = getState().activeVertical
        val newRoute = selectedVertical.mapToRoute()

        if (activeVertical != selectedVertical) {
            navigatorContract.navigateTo(
                route = newRoute,
                popUpToRoute = activeVertical.mapToRoute(),
                inclusive = true,
                isSingleTop = true,
            )
            updateState { copy(activeVertical = selectedVertical) }
        }
    }

    private fun ScreenHostContract.Event.OnNewRouteReceived.handleNewRouteReceive() =
        withUseCaseScope {
            launch {
                if (getState().shouldDisplayNavigationBar.not()) delay(NAVIGATION_BAR_DELAY)
                val targetRoute = this@handleNewRouteReceive.route

                updateState {
                    copy(
                        shouldDisplayNavigationBar = targetRoute != null &&
                            targetRoute in ALLOWED_NAVIGATION_BAR_ROUTES,
                    )
                }
            }
        }

    private companion object {
        val ALLOWED_NAVIGATION_BAR_ROUTES = setOf(
            ScreenRoute.Home,
            ScreenRoute.Leaderboard,
            ScreenRoute.Profile,
        )
        const val NAVIGATION_BAR_DELAY = 1000L
    }
}
