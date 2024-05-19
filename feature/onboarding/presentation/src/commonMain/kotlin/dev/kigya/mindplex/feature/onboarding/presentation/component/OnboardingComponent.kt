package dev.kigya.mindplex.feature.onboarding.presentation.component

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.replaceAll
import dev.kigya.mindplex.core.domain.interactor.base.None
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

@Stable
class OnboardingComponent(
    componentContext: ComponentContext,
    private val navigation: StackNavigation<Configuration>,
    private val setOnboardingCompletedUseCase: SetOnboardingCompletedUseCase,
) : BaseComponent<OnboardingContract.State, OnboardingContract.Effect>(
    componentContext = componentContext,
    initialState = OnboardingContract.State(),
),
    OnboardingContract {

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

            is OnboardingContract.Event.OnNextClicked ->
                if (event.currentPage == getState().onboardingData.size - 1) {
                    navigation.replaceAll(Configuration.HomeScreen)
                } else {
                    sendEffect(OnboardingContract.Effect.ScrollToPage(pageTo = event.currentPage + 1))
                }

            is OnboardingContract.Event.OnSkipClicked -> {
                setOnboardingCompletedUseCase(None)
                navigation.replaceAll(Configuration.HomeScreen)
            }
        }
    }

    private fun getOnboardingData(): ImmutableList<OnboardingScreenUiModel> = persistentListOf(
        OnboardingScreenUiModel(
            lottiePath = ResourceProvider.Lottie.ONBOARDING_FIRST,
            lottieDrawableResource = ResourceProvider.Drawable.IMAGE_ONBOARDING_FIRST,
            titleTextResource = ResourceProvider.Strings.ONBOARDING_FIRST_TITLE,
            descriptionTextResource = ResourceProvider.Strings.ONBOARDING_FIRST_DESCRIPTION,
            page = 0,
            skipButtonTextResource = ResourceProvider.Strings.ONBOARDING_SKIP_BUTTON_TEXT,
            nextButtonTextResource = ResourceProvider.Strings.ONBOARDING_NEXT_BUTTON_TEXT,
        ),
        OnboardingScreenUiModel(
            lottiePath = ResourceProvider.Lottie.ONBOARDING_SECOND,
            lottieDrawableResource = ResourceProvider.Drawable.IMAGE_ONBOARDING_SECOND,
            titleTextResource = ResourceProvider.Strings.ONBOARDING_SECOND_TITLE,
            descriptionTextResource = ResourceProvider.Strings.ONBOARDING_SECOND_DESCRIPTION,
            page = 1,
            skipButtonTextResource = ResourceProvider.Strings.ONBOARDING_SKIP_BUTTON_TEXT,
            nextButtonTextResource = ResourceProvider.Strings.ONBOARDING_NEXT_BUTTON_TEXT,
        ),
        OnboardingScreenUiModel(
            lottiePath = ResourceProvider.Lottie.ONBOARDING_THIRD,
            lottieDrawableResource = ResourceProvider.Drawable.IMAGE_ONBOARDING_THIRD,
            titleTextResource = ResourceProvider.Strings.ONBOARDING_THIRD_TITLE,
            descriptionTextResource = ResourceProvider.Strings.ONBOARDING_THIRD_DESCRIPTION,
            page = 2,
            skipButtonTextResource = null,
            nextButtonTextResource = ResourceProvider.Strings.ONBOARDING_GET_STARTED_BUTTON_TEXT,
        ),
    )

    private companion object {
        val stateFirstLaunchDelayedUpdatesDuration = 400.milliseconds
    }
}
