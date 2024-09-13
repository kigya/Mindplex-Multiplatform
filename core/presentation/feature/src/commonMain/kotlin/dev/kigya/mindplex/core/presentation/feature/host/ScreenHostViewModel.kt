package dev.kigya.mindplex.core.presentation.feature.host

import dev.kigya.mindplex.core.presentation.feature.BaseViewModel
import dev.kigya.mindplex.core.presentation.feature.contract.ScreenHostContract
import dev.kigya.mindplex.core.presentation.feature.contract.ScreenHostContract.Effect
import dev.kigya.mindplex.core.presentation.feature.contract.ScreenHostContract.Event
import dev.kigya.mindplex.core.presentation.feature.contract.ScreenHostContract.State
import dev.kigya.mindplex.navigation.navigator.navigator.AppNavigatorContract
import dev.kigya.mindplex.navigation.navigator.route.ScreenRoute

class ScreenHostViewModel(
    private val navigatorContract: AppNavigatorContract,
) : BaseViewModel<State, Effect>(State()), ScreenHostContract {

    val navigationChannel = navigatorContract.navigationChannel

    override fun handleEvent(event: Event) = withUseCaseScope {
        event.run {
            when (this) {
                is Event.OnHomeVerticalClicked -> handleVerticalClick(State.Vertical.HOME)
                is Event.OnLeaderboardVerticalClicked -> handleVerticalClick(State.Vertical.LEADERBOARD)
                is Event.OnProfileVerticalClicked -> handleVerticalClick(State.Vertical.PROFILE)
                is Event.OnNewRouteReceived -> handleNewRouteReceive()
            }
        }
    }

    private suspend fun handleVerticalClick(vertical: State.Vertical) {
        if (getState().activeVertical != vertical) {
            updateState { copy(activeVertical = vertical) }
            navigatorContract.navigateTo(
                route = when (vertical) {
                    State.Vertical.HOME -> ScreenRoute.HOME
                    State.Vertical.LEADERBOARD -> ScreenRoute.LEADERBOARD
                    State.Vertical.PROFILE -> ScreenRoute.PROFILE
                },
                popUpToRoute = ScreenRoute.HOME,
                inclusive = true,
            )
        }
    }

    private fun Event.OnNewRouteReceived.handleNewRouteReceive() {
        println("DHHDHDDHDH" + this.route)
        updateState {
            val targetRoute = this@handleNewRouteReceive.route
            copy(
                shouldDisplayNavigationBar = targetRoute.isNullOrEmpty().not() &&
                    targetRoute in ALLOWED_NAVIGATION_BAR_ROUTES,
            )
        }
    }

    private companion object {
        val ALLOWED_NAVIGATION_BAR_ROUTES = setOf(
            ScreenRoute.HOME,
            ScreenRoute.LEADERBOARD,
            ScreenRoute.PROFILE,
        )
    }
}
