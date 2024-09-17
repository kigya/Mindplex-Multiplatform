package dev.kigya.mindplex.feature.profile.presentation.contract

import androidx.compose.runtime.Immutable
import dev.kigya.mindplex.core.presentation.feature.CopyableComponentState
import dev.kigya.mindplex.core.presentation.feature.UnidirectionalViewModelContract

interface ProfileContract :
    UnidirectionalViewModelContract<ProfileContract.State, ProfileContract.Event, ProfileContract.Effect> {
    @Immutable
    data class State internal constructor(
        val mock: Int = 0,
    ) : CopyableComponentState

    @Immutable
    sealed class Event

    @Immutable
    sealed class Effect
}
