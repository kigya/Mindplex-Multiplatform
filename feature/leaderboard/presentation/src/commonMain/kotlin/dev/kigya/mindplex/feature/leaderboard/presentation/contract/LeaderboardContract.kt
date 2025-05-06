package dev.kigya.mindplex.feature.leaderboard.presentation.contract

import androidx.compose.runtime.Immutable
import dev.kigya.mindplex.core.presentation.feature.CopyableComponentState
import dev.kigya.mindplex.core.presentation.feature.UnidirectionalViewModelContract
import dev.kigya.mindplex.core.presentation.uikit.StubErrorType
import dev.kigya.mindplex.core.util.extension.empty
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

interface LeaderboardContract :
    UnidirectionalViewModelContract<LeaderboardContract.State, LeaderboardContract.Event, LeaderboardContract.Effect> {
    @ConsistentCopyVisibility
    @Immutable
    data class State internal constructor(
        val stubErrorType: StubErrorType? = null,
        val isLoading: Boolean = true,
        val podiumUsers: ImmutableList<UserCardData> = persistentListOf(),
        val nonPodiumUsers: ImmutableList<UserCardData> = persistentListOf(),
    ) : CopyableComponentState {

        @ConsistentCopyVisibility
        @Immutable
        data class UserCardData internal constructor(
            val id: String = String.empty,
            val userName: String = String.empty,
            val avatarUrl: String? = null,
            val countryCode: String? = null,
            val userScore: String = String.empty,
            val userRank: String = String.empty,
        )
    }

    @Immutable
    sealed class Event {

        internal data object OnErrorStubClicked : Event()
    }

    @Immutable
    sealed class Effect

    companion object {
        internal const val PODIUM_SIZE = 3
        internal const val NON_PODIUM_SIZE = 7
    }
}
