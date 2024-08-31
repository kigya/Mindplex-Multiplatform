package dev.kigya.mindplex.feature.onboarding.presentation.contract

import androidx.annotation.IntRange
import androidx.compose.runtime.Immutable
import dev.kigya.mindplex.core.presentation.feature.CopyableComponentState
import dev.kigya.mindplex.core.presentation.feature.UnidirectionalViewModelContract
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

interface OnboardingContract :
    UnidirectionalViewModelContract<OnboardingContract.State, OnboardingContract.Event, OnboardingContract.Effect> {
    @Immutable
    data class State internal constructor(
        val onboardingData: ImmutableList<OnboardingScreenData> = persistentListOf(),
        val shouldDisplayTitle: Boolean = false,
        val shouldDisplayDescription: Boolean = false,
        val shouldDisplayDotsIndicator: Boolean = false,
    ) : CopyableComponentState {
        @Immutable
        data class OnboardingScreenData internal constructor(
            val lottiePath: String? = null,
            val lottieDrawableResource: DrawableResource? = null,
            val titleTextResource: StringResource? = null,
            val descriptionTextResource: StringResource? = null,
            @IntRange(
                from = FIRST_PAGE_INDEX.toLong(),
                to = LAST_PAGE_INDEX.toLong(),
            ) val page: Int = FIRST_PAGE_INDEX,
            val skipButtonTextResource: StringResource? = null,
            val nextButtonTextResource: StringResource? = null,
        ) {
            private companion object {
                const val FIRST_PAGE_INDEX = 0
                const val LAST_PAGE_INDEX = 2
            }
        }
    }

    @Immutable
    sealed class Event {
        internal data object OnFirstLaunch : Event()
        internal data object OnSkipClicked : Event()
        internal data class OnNextClicked(val currentPage: Int) : Event()
    }

    @Immutable
    sealed class Effect {
        data class ScrollToPage internal constructor(val pageTo: Int) : Effect()
    }
}
