package dev.kigya.mindplex.feature.onboarding.presentation.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.replaceAll
import dev.kigya.core.domain.interactor.base.None
import dev.kigya.mindplex.core.presentation.feature.component.BaseComponent
import dev.kigya.mindplex.core.presentation.resources.ResourceProvider
import dev.kigya.mindplex.feature.onboarding.domain.usecase.SetOnboardingCompletedUseCase
import dev.kigya.mindplex.feature.onboarding.presentation.contract.OnboardingContract
import dev.kigya.mindplex.feature.onboarding.presentation.model.OnboardingScreenUiModel
import dev.kigya.mindplex.navigation.navigator.Configuration
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

class OnboardingComponent(
    componentContext: ComponentContext,
    private val navigation: StackNavigation<Configuration>,
    private val setOnboardingCompletedUseCase: SetOnboardingCompletedUseCase,
) : BaseComponent<OnboardingContract.State, OnboardingContract.Effect>(
    componentContext = componentContext,
    initialState = OnboardingContract.State(),
), OnboardingContract {

    override fun handleEvent(event: OnboardingContract.Event) = withUseCaseScope {
        when (event) {
            is OnboardingContract.Event.OnFirstLaunch -> {
                updateState { copy(onboardingData = getOnboardingData()) }
                withNonBlockingScope {
                    delay(stateFirstLaunchDelayedUpdatesDuration)
                    updateState { copy(shouldDisplayTitle = true) }
                    delay(stateFirstLaunchDelayedUpdatesDuration)
                    updateState { copy(shouldDisplayDescription = true) }
                    delay(stateFirstLaunchDelayedUpdatesDuration)
                    updateState { copy(shouldDisplayDotsIndicator = true) }
                }
            }

            is OnboardingContract.Event.OnNextClicked -> {
                if (event.currentPage == getState().onboardingData.size - 1) {
                    navigation.replaceAll(Configuration.HomeScreen)
                } else {
                    sendEffect(OnboardingContract.Effect.ScrollToPage(pageTo = event.currentPage + 1))
                }
            }

            is OnboardingContract.Event.OnSkipClicked -> {
                setOnboardingCompletedUseCase(None)
                navigation.replaceAll(Configuration.HomeScreen)
            }
        }
    }

    private fun getOnboardingData(): ImmutableList<OnboardingScreenUiModel> = persistentListOf(
        OnboardingScreenUiModel(
            lottiePath = ResourceProvider.Lottie.onboardingFirst,
            lottieDrawableResource = ResourceProvider.Drawable.imageOnboardingFirst,
            titleTextResource = ResourceProvider.Strings.onboardingFirstTitle,
            descriptionTextResource = ResourceProvider.Strings.onboardingFirstDescription,
            page = 0,
            skipButtonTextResource = ResourceProvider.Strings.onboardingSkipButtonText,
            nextButtonTextResource = ResourceProvider.Strings.onboardingNextButtonText,
        ), OnboardingScreenUiModel(
            lottiePath = ResourceProvider.Lottie.onboardingSecond,
            lottieDrawableResource = ResourceProvider.Drawable.imageOnboardingSecond,
            titleTextResource = ResourceProvider.Strings.onboardingSecondTitle,
            descriptionTextResource = ResourceProvider.Strings.onboardingSecondDescription,
            page = 1,
            skipButtonTextResource = ResourceProvider.Strings.onboardingSkipButtonText,
            nextButtonTextResource = ResourceProvider.Strings.onboardingNextButtonText,
        ), OnboardingScreenUiModel(
            lottiePath = ResourceProvider.Lottie.onboardingThird,
            lottieDrawableResource = ResourceProvider.Drawable.imageOnboardingThird,
            titleTextResource = ResourceProvider.Strings.onboardingThirdTitle,
            descriptionTextResource = ResourceProvider.Strings.onboardingThirdDescription,
            page = 2,
            skipButtonTextResource = null,
            nextButtonTextResource = ResourceProvider.Strings.onboardingGetStartedButtonText,
        )
    )

    private companion object {
        val stateFirstLaunchDelayedUpdatesDuration = 400.milliseconds
    }
}

