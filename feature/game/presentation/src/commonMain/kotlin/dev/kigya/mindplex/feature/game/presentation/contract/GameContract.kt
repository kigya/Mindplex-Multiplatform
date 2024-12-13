package dev.kigya.mindplex.feature.game.presentation.contract

import androidx.compose.runtime.Immutable
import dev.kigya.mindplex.core.presentation.component.StubErrorType
import dev.kigya.mindplex.core.presentation.feature.CopyableComponentState
import dev.kigya.mindplex.core.presentation.feature.UnidirectionalViewModelContract

interface GameContract :
    UnidirectionalViewModelContract<GameContract.State, GameContract.Event, GameContract.Effect> {
    @Immutable
    data class State internal constructor(
        val stubErrorType: StubErrorType? = null,
    ) : CopyableComponentState

    @Immutable
    sealed class Event {
        internal data object OnFirstLaunch : Event()

        internal data object OnErrorStubClicked : Event()
    }

    @Immutable
    sealed class Effect

    companion object {
        internal const val QUESTIONS_AMOUNT = 10
    }
}
