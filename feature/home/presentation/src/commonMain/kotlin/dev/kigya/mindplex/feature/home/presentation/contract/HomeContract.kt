package dev.kigya.mindplex.feature.home.presentation.contract

import androidx.annotation.Size
import androidx.compose.runtime.Immutable
import dev.kigya.mindplex.core.presentation.component.StubErrorType
import dev.kigya.mindplex.core.presentation.feature.CopyableComponentState
import dev.kigya.mindplex.core.presentation.feature.UnidirectionalViewModelContract
import dev.kigya.mindplex.core.util.extension.empty
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract.Companion.MAX_MODES_AMOUNT
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract.Companion.MIN_MODES_AMOUNT
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

interface HomeContract :
    UnidirectionalViewModelContract<HomeContract.State, HomeContract.Event, HomeContract.Effect> {
    @Immutable
    data class State internal constructor(
        val stubErrorType: StubErrorType? = null,
        val headerData: HeaderData = HeaderData(),
        val pagerData: PagerData = PagerData(),
        val modesData: ModesData = ModesData(),
    ) : CopyableComponentState {
        @Immutable
        data class HeaderData internal constructor(
            val userName: String = String.empty,
            val avatarUrl: String? = null,
            val isProfileNameLoading: Boolean = true,
            val isProfilePictureLoading: Boolean = true,
        )

        @Immutable
        data class PagerData internal constructor(
            val areFactsLoading: Boolean = true,
            @Size(value = FACTS_AMOUNT.toLong())
            val facts: ImmutableList<String> = persistentListOf(),
        )

        @Immutable
        data class ModesData internal constructor(
            val areModesLoading: Boolean = true,
            @HomeModesSize val modes: ImmutableList<Mode> = persistentListOf(),
        ) {
            data class Mode internal constructor(
                val type: Type = Type.RANDOM,
                val icon: DrawableResource? = null,
                val title: StringResource? = null,
                val description: StringResource? = null,
                val shouldScaleIcon: Boolean = false,
                val shouldDisplayDelimiter: Boolean = false,
            ) {
                enum class Type { PICK_ANSWER, TRUE_OR_FALSE, RANDOM }
            }
        }
    }

    @Immutable
    sealed class Event {
        internal data object OnFirstLaunch : Event()

        internal data object OnProfilePictureLoaded : Event()

        internal data object OnProfilePictureErrorReceived : Event()

        internal data object OnErrorStubClicked : Event()

        internal data class OnModeClicked(
            val type: State.ModesData.Mode.Type,
        ) : Event()

        internal data class OnModeClickStateChanged(
            @HomeModesSize val index: Int,
            val shouldScaleIcon: Boolean,
        ) : Event()
    }

    @Immutable
    sealed class Effect {
        internal data object ScrollToNextPage : Effect()
    }

    companion object {
        internal const val FACTS_AMOUNT = 3
        internal const val MIN_MODES_AMOUNT = 1L
        internal const val MAX_MODES_AMOUNT = 3L
    }
}

@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FIELD)
@Size(
    min = MIN_MODES_AMOUNT,
    max = MAX_MODES_AMOUNT,
)
private annotation class HomeModesSize
