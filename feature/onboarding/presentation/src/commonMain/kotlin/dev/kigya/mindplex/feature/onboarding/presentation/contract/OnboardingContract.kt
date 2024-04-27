package dev.kigya.mindplex.feature.onboarding.presentation.contract

import androidx.compose.runtime.Immutable
import dev.kigya.mindplex.core.presentation.feature.component.CopyableComponentState
import dev.kigya.mindplex.core.presentation.feature.component.UnidirectionalComponentContract
import dev.kigya.mindplex.feature.onboarding.presentation.model.OnboardingScreenUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

interface OnboardingContract :
    UnidirectionalComponentContract<OnboardingContract.State, OnboardingContract.Event, OnboardingContract.Effect> {
    @Immutable
    data class State(
        val onboardingData: ImmutableList<OnboardingScreenUiModel> = persistentListOf(),
        val shouldDisplayTitle: Boolean = false,
        val shouldDisplayDescription: Boolean = false,
        val shouldDisplayDotsIndicator: Boolean = false,
    ) : CopyableComponentState

    @Immutable
    sealed class Event {
        data object OnFirstLaunch : Event()
        data object OnSkipClicked : Event()
        data class OnNextClicked(val currentPage: Int) : Event()
    }

    @Immutable
    sealed class Effect {
        data class ScrollToPage(val pageTo: Int) : Effect()
    }
}
