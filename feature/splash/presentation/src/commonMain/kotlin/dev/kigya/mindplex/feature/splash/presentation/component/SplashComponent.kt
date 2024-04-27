package dev.kigya.mindplex.feature.splash.presentation.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.replaceAll
import dev.kigya.core.domain.interactor.base.None
import dev.kigya.mindplex.core.presentation.feature.component.BaseComponent
import dev.kigya.mindplex.feature.onboarding.domain.usecase.GetIsOnboardingCompletedUseCase
import dev.kigya.mindplex.feature.splash.presentation.contract.SplashContract
import dev.kigya.mindplex.navigation.navigator.Configuration
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlin.time.Duration.Companion.milliseconds

class SplashComponent(
    componentContext: ComponentContext,
    private val navigation: StackNavigation<Configuration>,
    private val getIsOnboardingCompletedUseCase: GetIsOnboardingCompletedUseCase,
) : BaseComponent<SplashContract.State, SplashContract.Effect>(
    componentContext = componentContext,
    initialState = SplashContract.State(),
), SplashContract {

    private val _isOnboardingCompleted = MutableStateFlow<Boolean?>(null)

    override fun handleEvent(event: SplashContract.Event) =
        withUseCaseScope {
            when (event) {
                SplashContract.Event.OnFirstLaunch -> {
                    getIsOnboardingCompletedUseCase(None).collect { isCompleted ->
                        _isOnboardingCompleted.update { isCompleted }
                    }
                }

                SplashContract.Event.OnAnimationFinished -> {
                    updateState { copy(shouldDisplayText = true) }
                    delay(postAnimationDelay)
                    if (_isOnboardingCompleted.value == true) {
                        navigation.replaceAll(Configuration.HomeScreen)
                    } else {
                        navigation.replaceAll(Configuration.OnboardingScreen)
                    }
                }
            }
        }

    private companion object {
        val postAnimationDelay = 700.milliseconds
    }
}
