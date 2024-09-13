package dev.kigya.mindplex.feature.leaderboard.presentation.contract

import androidx.compose.runtime.Immutable
import dev.kigya.mindplex.core.presentation.feature.CopyableComponentState
import dev.kigya.mindplex.core.presentation.feature.UnidirectionalViewModelContract

interface LeaderboardContract :
    UnidirectionalViewModelContract<LeaderboardContract.State, LeaderboardContract.Event, LeaderboardContract.Effect> {
    @Immutable
    data class State internal constructor(
        val mock: Int = 0,
    ) : CopyableComponentState

    @Immutable
    sealed class Event

    @Immutable
    sealed class Effect
}
