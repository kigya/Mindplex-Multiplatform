package dev.kigya.mindplex.feature.splash.presentation.ui

import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.core.domain.profile.usecase.CheckAppInDarkThemeUseCase
import dev.kigya.mindplex.core.domain.profile.usecase.UpdateAppInDarkThemeUsrCase
import dev.kigya.mindplex.core.presentation.feature.BaseViewModel
import dev.kigya.mindplex.feature.login.domain.usecase.GetIsUserSignedInUseCase
import dev.kigya.mindplex.feature.onboarding.domain.usecase.GetIsOnboardingCompletedUseCase
import dev.kigya.mindplex.feature.splash.presentation.contract.SplashContract
import dev.kigya.mindplex.navigation.navigator.navigator.MindplexNavigatorContract
import dev.kigya.mindplex.navigation.navigator.route.ScreenRoute
import dev.kigya.outcome.getOrNull
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kotlin.time.Duration.Companion.milliseconds

class SplashScreenViewModel(
    private val getIsOnboardingCompletedUseCase: GetIsOnboardingCompletedUseCase,
    private val getIsUserSignedInUseCase: GetIsUserSignedInUseCase,
    private val getThemeUseCase: CheckAppInDarkThemeUseCase,
    private val setThemeUseCase: UpdateAppInDarkThemeUsrCase,
    navigatorContract: MindplexNavigatorContract,
) : BaseViewModel<SplashContract.State, SplashContract.Effect>(
    navigatorContract = navigatorContract,
    initialState = SplashContract.State(),
),
    SplashContract {

    private val _isOnboardingCompleted = MutableStateFlow<Boolean?>(null)
    private val _isUserSignedIn = MutableStateFlow<Boolean?>(null)

    override fun executeStartAction() {
        withUseCaseScope {
            getThemeUseCase(None).getOrNull()
                ?: sendEffect(SplashContract.Effect.RequestSystemTheme)

            combine(
                getIsOnboardingCompletedUseCase(None),
                getIsUserSignedInUseCase(None),
            ) { isOnboardingCompleted, isUserSignedIn ->
                _isOnboardingCompleted.update { isOnboardingCompleted }
                _isUserSignedIn.update { isUserSignedIn }
            }.launchIn(this)
        }
    }

    override fun handleEvent(event: SplashContract.Event) {
        withUseCaseScope {
            when (event) {
                SplashContract.Event.OnAnimationFinished -> {
                    updateState { copy(shouldDisplayText = true) }
                    delay(postAnimationDelay)
                    val route = when {
                        _isOnboardingCompleted.value == false -> ScreenRoute.Onboarding
                        _isUserSignedIn.value == false -> ScreenRoute.Login
                        else -> ScreenRoute.Home
                    }
                    navigatorContract.navigateTo(
                        route = route,
                        popUpToRoute = ScreenRoute.Splash,
                        inclusive = true,
                    )
                }

                is SplashContract.Event.OnSystemThemeReceived -> setThemeUseCase(event.isDark)
            }
        }
    }

    private companion object {
        val postAnimationDelay = 700.milliseconds
    }
}
