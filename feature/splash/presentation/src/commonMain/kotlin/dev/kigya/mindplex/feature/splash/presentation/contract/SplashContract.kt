package dev.kigya.mindplex.feature.splash.presentation.contract

import androidx.compose.runtime.Immutable
import dev.kigya.mindplex.core.presentation.feature.CopyableComponentState
import dev.kigya.mindplex.core.presentation.feature.UnidirectionalViewModelContract

interface SplashContract :
    UnidirectionalViewModelContract<SplashContract.State, SplashContract.Event, SplashContract.Effect> {
    @Immutable
    data class State(val shouldDisplayText: Boolean = false) : CopyableComponentState

    @Immutable
    sealed class Event {
        data object OnFirstLaunch : Event()
        data object OnAnimationFinished : Event()
    }

    @Immutable
    sealed class Effect
}
