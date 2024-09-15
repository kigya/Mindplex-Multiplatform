package dev.kigya.mindplex.core.presentation.feature.host

import dev.kigya.mindplex.core.presentation.feature.BaseViewModel
import dev.kigya.mindplex.core.presentation.feature.contract.ScreenHostContract
import dev.kigya.mindplex.core.presentation.feature.contract.ScreenHostContract.Effect
import dev.kigya.mindplex.core.presentation.feature.contract.ScreenHostContract.Event
import dev.kigya.mindplex.core.presentation.feature.contract.ScreenHostContract.State
import dev.kigya.mindplex.navigation.navigator.navigator.AppNavigatorContract
import dev.kigya.mindplex.navigation.navigator.route.ScreenRoute
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ScreenHostViewModel(
    private val navigatorContract: AppNavigatorContract,
) : BaseViewModel<State, Effect>(State()), ScreenHostContract {

    val navigationChannel = navigatorContract.navigationChannel

    override fun handleEvent(event: Event) = withUseCaseScope {
        event.run {
            when (this) {
                is Event.OnHomeVerticalClicked -> handleVerticalClick(State.Vertical.Home)
                is Event.OnLeaderboardVerticalClicked -> handleVerticalClick(State.Vertical.Leaderboard)
                is Event.OnProfileVerticalClicked -> handleVerticalClick(State.Vertical.Profile)
                is Event.OnNewRouteReceived -> handleNewRouteReceive()
            }
        }
    }

    private suspend fun handleVerticalClick(selectedVertical: State.Vertical) {
        val activeVertical = getState().activeVertical

        if (activeVertical != selectedVertical) {
            navigatorContract.navigateTo(
                route = selectedVertical.mapToRoute(),
                popUpToRoute = activeVertical.mapToRoute(),
                inclusive = true,
                isSingleTop = true,
            )
            updateState { copy(activeVertical = selectedVertical) }
        }
    }

    private fun Event.OnNewRouteReceived.handleNewRouteReceive() = withUseCaseScope {
        launch {
            if (getState().shouldDisplayNavigationBar.not()) delay(NAVIGATION_BAR_DELAY)

            updateState {
                val targetRoute = this@handleNewRouteReceive.route
                copy(
                    shouldDisplayNavigationBar = targetRoute.isNullOrEmpty().not() &&
                        targetRoute in ALLOWED_NAVIGATION_BAR_ROUTES,
                )
            }
        }
    }

    private companion object {
        val ALLOWED_NAVIGATION_BAR_ROUTES = setOf(
            ScreenRoute.HOME,
            ScreenRoute.LEADERBOARD,
            ScreenRoute.PROFILE,
        )
        const val NAVIGATION_BAR_DELAY = 1000L
    }
}
