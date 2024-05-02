package dev.kigya.mindplex.feature.home.presentation.contract

import androidx.compose.runtime.Immutable
import dev.kigya.mindplex.core.presentation.feature.component.CopyableComponentState
import dev.kigya.mindplex.core.presentation.feature.component.UnidirectionalComponentContract

interface HomeContract :
    UnidirectionalComponentContract<HomeContract.State, HomeContract.Event, HomeContract.Effect> {
    @Immutable
    data class State(val mock: String = "") : CopyableComponentState

    @Immutable
    sealed class Event {
        data object OnFirstLaunch : Event()
    }

    @Immutable
    sealed class Effect
}
