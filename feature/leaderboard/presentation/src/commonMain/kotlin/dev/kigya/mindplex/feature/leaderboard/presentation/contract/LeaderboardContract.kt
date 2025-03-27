package dev.kigya.mindplex.feature.leaderboard.presentation.contract

import androidx.annotation.Size
import androidx.compose.runtime.Immutable
import dev.kigya.mindplex.core.presentation.feature.CopyableComponentState
import dev.kigya.mindplex.core.presentation.feature.UnidirectionalViewModelContract
import dev.kigya.mindplex.core.presentation.uikit.StubErrorType
import dev.kigya.mindplex.core.util.extension.empty
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

interface LeaderboardContract :
    UnidirectionalViewModelContract<LeaderboardContract.State, LeaderboardContract.Event, LeaderboardContract.Effect> {
    @Immutable
    data class State internal constructor(
        val stubErrorType: StubErrorType? = null,
        val podiumData: PodiumData = PodiumData(),
        val leaderboardLoading: LeaderboardScreenLoadingData = LeaderboardScreenLoadingData(),
        val userCardData: ImmutableList<UserCardData> = RANGE_SHIMMER_CARDS
            .map { UserCardData() }
            .toPersistentList(),
    ) : CopyableComponentState {

        @ConsistentCopyVisibility
        @Immutable
        data class PodiumData internal constructor(
            @Size(value = PLACE_AMOUNT.toLong())
            val place: ImmutableList<String> = persistentListOf(),
            val currentIndex: Int = 0,
        )

        @ConsistentCopyVisibility
        @Immutable
        data class UserCardData internal constructor(
            val userName: String = String.empty,
            val avatarUrl: String? = null,
            val userScore: String = String.empty,
            val userPlace: String = String.empty,
            val place: ImmutableList<String> = persistentListOf(),
        )

        @ConsistentCopyVisibility
        @Immutable
        data class LeaderboardScreenLoadingData internal constructor(
            val isLeaderboardLoading: Boolean = true,
        )
    }

    @Immutable
    sealed class Event {

        internal data object OnErrorStubClicked : Event()

        internal data object OnLeaderboardLoaded : Event()
    }

    @Immutable
    sealed class Effect

    companion object {
        internal const val PLACE_AMOUNT = 3
        private const val SHIMMER_START = 0
        private const val SHIMMER_END = 9
        internal val RANGE_SHIMMER_CARDS = SHIMMER_START..SHIMMER_END
    }
}
