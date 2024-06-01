package dev.kigya.mindplex.feature.splash.presentation.ui

import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.core.presentation.feature.BaseViewModel
import dev.kigya.mindplex.feature.onboarding.domain.usecase.GetIsOnboardingCompletedUseCase
import dev.kigya.mindplex.feature.splash.presentation.contract.SplashContract
import dev.kigya.mindplex.navigation.navigator.navigator.AppNavigatorContract
import dev.kigya.mindplex.navigation.navigator.route.ScreenRoute
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlin.time.Duration.Companion.milliseconds

class SplashScreenViewModel(
    private val navigatorContract: AppNavigatorContract,
    private val getIsOnboardingCompletedUseCase: GetIsOnboardingCompletedUseCase,
) : BaseViewModel<SplashContract.State, SplashContract.Effect>(SplashContract.State()),
    SplashContract {

    private val _isOnboardingCompleted = MutableStateFlow<Boolean?>(null)

    override fun handleEvent(event: SplashContract.Event) = withUseCaseScope {
        when (event) {
            SplashContract.Event.OnFirstLaunch ->
                getIsOnboardingCompletedUseCase(None).collect { isCompleted ->
                    _isOnboardingCompleted.update { isCompleted }
                }

            SplashContract.Event.OnAnimationFinished -> {
                updateState { copy(shouldDisplayText = true) }
                delay(POST_ANIMATION_DELAY)
                if (_isOnboardingCompleted.value == true) {
                    navigatorContract.navigateTo(
                        route = ScreenRoute.HOME,
                        popUpToRoute = ScreenRoute.SPLASH,
                        inclusive = true,
                    )
                } else {
                    navigatorContract.navigateTo(
                        route = ScreenRoute.ONBOARDING,
                        popUpToRoute = ScreenRoute.SPLASH,
                        inclusive = true,
                    )
                }
            }
        }
    }

    private companion object {
        val POST_ANIMATION_DELAY = 700.milliseconds
    }
}
