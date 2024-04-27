package dev.kigya.mindplex.feature.splash.presentation.contract

import androidx.compose.runtime.Immutable
import dev.kigya.mindplex.core.presentation.feature.component.CopyableComponentState
import dev.kigya.mindplex.core.presentation.feature.component.UnidirectionalComponentContract

interface SplashContract :
    UnidirectionalComponentContract<SplashContract.State, SplashContract.Event, SplashContract.Effect> {
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
