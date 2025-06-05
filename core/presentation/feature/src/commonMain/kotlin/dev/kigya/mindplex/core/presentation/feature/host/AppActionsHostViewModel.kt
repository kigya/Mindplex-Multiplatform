package dev.kigya.mindplex.core.presentation.feature.host

import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.core.presentation.feature.BaseViewModel
import dev.kigya.mindplex.core.presentation.feature.contract.ScreenHostContract
import dev.kigya.mindplex.feature.login.domain.usecase.GetIsUserSignedInUseCase
import dev.kigya.mindplex.feature.onboarding.domain.usecase.GetIsOnboardingCompletedUseCase
import dev.kigya.mindplex.navigation.navigator.navigator.MindplexNavigatorContract
import dev.kigya.mindplex.navigation.navigator.route.ScreenRoute
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AppActionsHostViewModel(
    private val getIsOnboardingCompletedUseCase: GetIsOnboardingCompletedUseCase,
    private val getIsUserSignedInUseCase: GetIsUserSignedInUseCase,
    navigatorContract: MindplexNavigatorContract,
) : BaseViewModel<ScreenHostContract.State, ScreenHostContract.Effect>(
    navigatorContract = navigatorContract,
    initialState = ScreenHostContract.State(),
),
    ScreenHostContract {

    private val _startDestination = MutableStateFlow<ScreenRoute?>(null)
    val startDestination = _startDestination.asStateFlow()

    val navigationChannel = navigatorContract.navigationChannel

    init {
        withUseCaseScope {
            determineStartDestination()
        }
    }

    private suspend fun determineStartDestination() {
        val isOnboardingCompleted = getIsOnboardingCompletedUseCase(None).first()
        val isUserSignedIn = getIsUserSignedInUseCase(None).first()

        val route = when {
            isOnboardingCompleted.not() -> ScreenRoute.Onboarding
            isUserSignedIn.not() -> ScreenRoute.Login
            else -> ScreenRoute.Home
        }
        _startDestination.value = route
        handleEvent(ScreenHostContract.Event.OnNewRouteReceived(route))
    }

    override fun handleEvent(event: ScreenHostContract.Event) {
        withUseCaseScope {
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
                        activeVertical = if (targetRoute == ScreenRoute.Login) {
                            ScreenHostContract.State.Vertical.Home
                        } else {
                            getState().activeVertical
                        },
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
