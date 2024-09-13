package dev.kigya.mindplex.core.presentation.feature.contract

import androidx.compose.runtime.Immutable
import dev.kigya.mindplex.core.presentation.feature.CopyableComponentState
import dev.kigya.mindplex.core.presentation.feature.UnidirectionalViewModelContract

interface ScreenHostContract :
    UnidirectionalViewModelContract<ScreenHostContract.State, ScreenHostContract.Event, ScreenHostContract.Effect> {
    @Immutable
    data class State internal constructor(
        val shouldDisplayNavigationBar: Boolean = false,
        val activeVertical: Vertical = Vertical.HOME,
    ) : CopyableComponentState {

        @Immutable
        enum class Vertical(val index: Int) {
            HOME(HOME_INDEX),
            LEADERBOARD(LEADERBOARD_INDEX),
            PROFILE(PROFILE_INDEX),
        }
    }

    @Immutable
    sealed class Event {
        internal data object OnHomeVerticalClicked : Event()

        internal data object OnLeaderboardVerticalClicked : Event()

        internal data object OnProfileVerticalClicked : Event()

        internal data class OnNewRouteReceived(val route: String?) : Event()
    }

    @Immutable
    sealed class Effect

    companion object {
        private const val HOME_INDEX = 0
        private const val LEADERBOARD_INDEX = 1
        private const val PROFILE_INDEX = 2
    }
}
