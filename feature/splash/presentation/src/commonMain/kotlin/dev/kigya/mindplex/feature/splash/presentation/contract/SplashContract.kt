package dev.kigya.mindplex.feature.splash.presentation.contract

import androidx.compose.runtime.Immutable
import dev.kigya.mindplex.core.presentation.feature.CopyableComponentState
import dev.kigya.mindplex.core.presentation.feature.UnidirectionalViewModelContract

interface SplashContract :
    UnidirectionalViewModelContract<SplashContract.State, SplashContract.Event, SplashContract.Effect> {

    @ConsistentCopyVisibility
    @Immutable
    data class State internal constructor(
        val shouldDisplayText: Boolean = false,
    ) : CopyableComponentState

    @Immutable
    sealed class Event {

        internal data object OnAnimationFinished : Event()
        internal data class OnSystemThemeReceived(val isDark: Boolean) : Event()
    }

    @Immutable
    sealed class Effect {
        internal data object RequestSystemTheme : Effect()
    }
}
