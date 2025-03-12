package dev.kigya.mindplex.feature.onboarding.presentation.contract

import androidx.annotation.IntRange
import androidx.annotation.Size
import androidx.compose.runtime.Immutable
import dev.kigya.mindplex.core.presentation.feature.CopyableComponentState
import dev.kigya.mindplex.core.presentation.feature.UnidirectionalViewModelContract
import dev.kigya.mindplex.feature.onboarding.presentation.contract.OnboardingContract.State.OnboardingScreenData.Companion.FIRST_PAGE_INDEX
import dev.kigya.mindplex.feature.onboarding.presentation.contract.OnboardingContract.State.OnboardingScreenData.Companion.LAST_PAGE_INDEX
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.StringResource

interface OnboardingContract :
    UnidirectionalViewModelContract<OnboardingContract.State, OnboardingContract.Event, OnboardingContract.Effect> {
    @ConsistentCopyVisibility
    @Immutable
    data class State internal constructor(
        @Size(
            min = MIN_PAGES_AMOUNT,
            max = MAX_PAGES_AMOUNT,
        )
        val onboardingData: ImmutableList<OnboardingScreenData> = persistentListOf(),
        val shouldDisplayTitle: Boolean = false,
        val shouldDisplayDescription: Boolean = false,
        val shouldDisplayDotsIndicator: Boolean = false,
    ) : CopyableComponentState {
        @ConsistentCopyVisibility
        @Immutable
        data class OnboardingScreenData internal constructor(
            val lottiePath: String? = null,
            val titleTextResource: StringResource? = null,
            val descriptionTextResource: StringResource? = null,
            @OnboardingIndexRange val page: Int = FIRST_PAGE_INDEX,
            val skipButtonTextResource: StringResource? = null,
            val nextButtonTextResource: StringResource? = null,
        ) {
            internal companion object {
                const val FIRST_PAGE_INDEX = 0
                const val LAST_PAGE_INDEX = 2
            }
        }

        private companion object {
            const val MIN_PAGES_AMOUNT = 1L
            const val MAX_PAGES_AMOUNT = 3L
        }
    }

    @Immutable
    sealed class Event {

        internal data object OnSkipClicked : Event()

        internal data class OnNextClicked(
            @OnboardingIndexRange
            val currentPage: Int,
        ) : Event()
    }

    @Immutable
    sealed class Effect {
        @ConsistentCopyVisibility
        data class ScrollToPage internal constructor(
            @OnboardingIndexRange
            val pageTo: Int,
        ) : Effect()
    }
}

@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FIELD)
@IntRange(
    from = FIRST_PAGE_INDEX.toLong(),
    to = LAST_PAGE_INDEX.toLong(),
)
private annotation class OnboardingIndexRange
