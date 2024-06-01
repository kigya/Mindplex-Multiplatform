package dev.kigya.mindplex.feature.onboarding.presentation.ui

import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.core.presentation.feature.BaseViewModel
import dev.kigya.mindplex.feature.onboarding.domain.usecase.SetOnboardingCompletedUseCase
import dev.kigya.mindplex.feature.onboarding.presentation.contract.OnboardingContract
import dev.kigya.mindplex.feature.onboarding.presentation.model.OnboardingScreenUiModel
import dev.kigya.mindplex.navigation.navigator.navigator.AppNavigatorContract
import dev.kigya.mindplex.navigation.navigator.route.ScreenRoute
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import mindplex_multiplatform.feature.onboarding.presentation.generated.resources.Res
import mindplex_multiplatform.feature.onboarding.presentation.generated.resources.im_onboarding_first
import mindplex_multiplatform.feature.onboarding.presentation.generated.resources.im_onboarding_second
import mindplex_multiplatform.feature.onboarding.presentation.generated.resources.im_onboarding_third
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
    private val navigatorContract: AppNavigatorContract,
    private val setOnboardingCompletedUseCase: SetOnboardingCompletedUseCase,
) : BaseViewModel<OnboardingContract.State, OnboardingContract.Effect>(OnboardingContract.State()),
    OnboardingContract {

    override fun handleEvent(event: OnboardingContract.Event) = withUseCaseScope {
        when (event) {
            is OnboardingContract.Event.OnFirstLaunch -> {
                updateState { copy(onboardingData = getOnboardingData()) }
                delay(STATE_FIRST_LAUNCH_UPDATES_DELAYED_TRANSITION)
                updateState { copy(shouldDisplayTitle = true) }
                delay(STATE_FIRST_LAUNCH_UPDATES_DELAYED_TRANSITION)
                updateState { copy(shouldDisplayDescription = true) }
                delay(STATE_FIRST_LAUNCH_UPDATES_DELAYED_TRANSITION)
                updateState { copy(shouldDisplayDotsIndicator = true) }
            }

            is OnboardingContract.Event.OnNextClicked ->
                if (event.currentPage == getState().onboardingData.lastIndex) {
                    navigatorContract.navigateTo(
                        route = ScreenRoute.HOME,
                        popUpToRoute = ScreenRoute.SPLASH,
                        inclusive = true,
                    )
                } else {
                    sendEffect(OnboardingContract.Effect.ScrollToPage(pageTo = event.currentPage + 1))
                }

            is OnboardingContract.Event.OnSkipClicked -> {
                setOnboardingCompletedUseCase(None)
                navigatorContract.navigateTo(
                    route = ScreenRoute.HOME,
                    popUpToRoute = ScreenRoute.SPLASH,
                    inclusive = true,
                )
            }
        }
    }

    private fun getOnboardingData(): ImmutableList<OnboardingScreenUiModel> = persistentListOf(
        OnboardingScreenUiModel(
            lottiePath = "files/onboarding_first.json",
            lottieDrawableResource = Res.drawable.im_onboarding_first,
            titleTextResource = Res.string.onboarding_first_title,
            descriptionTextResource = Res.string.onboarding_first_description,
            page = 0,
            skipButtonTextResource = Res.string.onboarding_skip_button_text,
            nextButtonTextResource = Res.string.onboarding_next_button_text,
        ),
        OnboardingScreenUiModel(
            lottiePath = "files/onboarding_second.json",
            lottieDrawableResource = Res.drawable.im_onboarding_second,
            titleTextResource = Res.string.onboarding_second_title,
            descriptionTextResource = Res.string.onboarding_second_description,
            page = 1,
            skipButtonTextResource = Res.string.onboarding_skip_button_text,
            nextButtonTextResource = Res.string.onboarding_next_button_text,
        ),
        OnboardingScreenUiModel(
            lottiePath = "files/onboarding_third.json",
            lottieDrawableResource = Res.drawable.im_onboarding_third,
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
