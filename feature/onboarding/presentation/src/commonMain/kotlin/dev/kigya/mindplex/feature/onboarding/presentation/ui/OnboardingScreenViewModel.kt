package dev.kigya.mindplex.feature.onboarding.presentation.ui

import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.core.presentation.feature.BaseViewModel
import dev.kigya.mindplex.feature.onboarding.domain.usecase.SetOnboardingCompletedUseCase
import dev.kigya.mindplex.feature.onboarding.presentation.contract.OnboardingContract
import dev.kigya.mindplex.navigation.navigator.navigator.MindplexNavigatorContract
import dev.kigya.mindplex.navigation.navigator.route.ScreenRoute
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import mindplex_multiplatform.feature.onboarding.presentation.generated.resources.Res
import mindplex_multiplatform.feature.onboarding.presentation.generated.resources.onboarding_first_description
import mindplex_multiplatform.feature.onboarding.presentation.generated.resources.onboarding_first_title
import mindplex_multiplatform.feature.onboarding.presentation.generated.resources.onboarding_get_started_button_text
import mindplex_multiplatform.feature.onboarding.presentation.generated.resources.onboarding_next_button_text
import mindplex_multiplatform.feature.onboarding.presentation.generated.resources.onboarding_second_description
import mindplex_multiplatform.feature.onboarding.presentation.generated.resources.onboarding_second_title
import mindplex_multiplatform.feature.onboarding.presentation.generated.resources.onboarding_skip_button_text
import mindplex_multiplatform.feature.onboarding.presentation.generated.resources.onboarding_third_description
import mindplex_multiplatform.feature.onboarding.presentation.generated.resources.onboarding_third_title
import kotlin.time.Duration.Companion.milliseconds

class OnboardingScreenViewModel(
    private val setOnboardingCompletedUseCase: SetOnboardingCompletedUseCase,
    navigatorContract: MindplexNavigatorContract,
) : BaseViewModel<OnboardingContract.State, OnboardingContract.Effect>(
    navigatorContract = navigatorContract,
    initialState = OnboardingContract.State(),
),
    OnboardingContract {

    override fun executeStartAction() = withUseCaseScope {
        updateState { copy(onboardingData = getOnboardingData()) }
        delay(STATE_FIRST_LAUNCH_UPDATES_DELAYED_TRANSITION)
        updateState { copy(shouldDisplayTitle = true) }
        delay(STATE_FIRST_LAUNCH_UPDATES_DELAYED_TRANSITION)
        updateState { copy(shouldDisplayDescription = true) }
        delay(STATE_FIRST_LAUNCH_UPDATES_DELAYED_TRANSITION)
        updateState { copy(shouldDisplayDotsIndicator = true) }
    }

    override fun handleEvent(event: OnboardingContract.Event) = withUseCaseScope {
        when (event) {
            is OnboardingContract.Event.OnNextClicked ->
                if (event.currentPage == getState().onboardingData.lastIndex) {
                    setOnboardingCompletedUseCase(None)
                    navigatorContract.navigateTo(
                        route = ScreenRoute.Login,
                        popUpToRoute = ScreenRoute.Onboarding,
                        inclusive = true,
                    )
                } else {
                    sendEffect(OnboardingContract.Effect.ScrollToPage(pageTo = event.currentPage + 1))
                }

            is OnboardingContract.Event.OnSkipClicked -> {
                setOnboardingCompletedUseCase(None)
                navigatorContract.navigateTo(
                    route = ScreenRoute.Login,
                    popUpToRoute = ScreenRoute.Onboarding,
                    inclusive = true,
                )
            }
        }
    }

    private fun getOnboardingData(): ImmutableList<OnboardingContract.State.OnboardingScreenData> =
        persistentListOf(
            OnboardingContract.State.OnboardingScreenData(
                lottiePath = "files/onboarding_first.json",
                titleTextResource = Res.string.onboarding_first_title,
                descriptionTextResource = Res.string.onboarding_first_description,
                page = 0,
                skipButtonTextResource = Res.string.onboarding_skip_button_text,
                nextButtonTextResource = Res.string.onboarding_next_button_text,
            ),
            OnboardingContract.State.OnboardingScreenData(
                lottiePath = "files/onboarding_second.json",
                titleTextResource = Res.string.onboarding_second_title,
                descriptionTextResource = Res.string.onboarding_second_description,
                page = 1,
                skipButtonTextResource = Res.string.onboarding_skip_button_text,
                nextButtonTextResource = Res.string.onboarding_next_button_text,
            ),
            OnboardingContract.State.OnboardingScreenData(
                lottiePath = "files/onboarding_third.json",
                titleTextResource = Res.string.onboarding_third_title,
                descriptionTextResource = Res.string.onboarding_third_description,
                page = 2,
                skipButtonTextResource = null,
                nextButtonTextResource = Res.string.onboarding_get_started_button_text,
            ),
        )

    private companion object {
        val STATE_FIRST_LAUNCH_UPDATES_DELAYED_TRANSITION = 400.milliseconds
    }
}
