package dev.kigya.mindplex.feature.splash.presentation.ui

import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.core.domain.profile.usecase.CheckAppInDarkThemeUseCase
import dev.kigya.mindplex.core.domain.profile.usecase.UpdateAppInDarkThemeUsrCase
import dev.kigya.mindplex.core.presentation.feature.BaseViewModel
import dev.kigya.mindplex.feature.splash.presentation.contract.SplashContract
import dev.kigya.mindplex.navigation.navigator.navigator.MindplexNavigatorContract
import dev.kigya.outcome.getOrNull
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

class SplashScreenViewModel(
    private val getThemeUseCase: CheckAppInDarkThemeUseCase,
    private val setThemeUseCase: UpdateAppInDarkThemeUsrCase,
    navigatorContract: MindplexNavigatorContract,
) : BaseViewModel<SplashContract.State, SplashContract.Effect>(
    navigatorContract = navigatorContract,
    initialState = SplashContract.State(),
),
    SplashContract {

    override fun executeStartAction() {
        withUseCaseScope {
            getThemeUseCase(None).getOrNull()
                ?: sendEffect(SplashContract.Effect.RequestSystemTheme)
        }
    }

    override fun handleEvent(event: SplashContract.Event) {
        withUseCaseScope {
            when (event) {
                SplashContract.Event.OnAnimationFinished -> {
                    updateState { copy(shouldDisplayText = true) }
                    delay(postAnimationDelay)
                    sendEffect(SplashContract.Effect.NavigationFinished)
                }

                is SplashContract.Event.OnSystemThemeReceived -> setThemeUseCase(event.isDark)
            }
        }
    }

    private companion object {
        val postAnimationDelay = 700.milliseconds
    }
}
