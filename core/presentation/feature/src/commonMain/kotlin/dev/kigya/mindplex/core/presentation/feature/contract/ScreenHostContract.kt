package dev.kigya.mindplex.core.presentation.feature.contract

import androidx.compose.runtime.Immutable
import dev.kigya.mindplex.core.presentation.feature.CopyableComponentState
import dev.kigya.mindplex.core.presentation.feature.UnidirectionalViewModelContract
import dev.kigya.mindplex.navigation.navigator.route.ScreenRoute

interface ScreenHostContract :
    UnidirectionalViewModelContract<ScreenHostContract.State, ScreenHostContract.Event, ScreenHostContract.Effect> {
    @Immutable
    data class State internal constructor(
        val shouldDisplayNavigationBar: Boolean = false,
        val activeVertical: Vertical = Vertical.Home,
    ) : CopyableComponentState {

        @Immutable
        sealed class Vertical(val index: Int) {
            internal data object Home : Vertical(HOME_INDEX)
            internal data object Leaderboard : Vertical(LEADERBOARD_INDEX)
            internal data object Profile : Vertical(PROFILE_INDEX)

            internal fun mapToRoute(): ScreenRoute = when (this) {
                Home -> ScreenRoute.Home
                Leaderboard -> ScreenRoute.Leaderboard
                Profile -> ScreenRoute.Profile
            }
        }
    }

    @Immutable
    sealed class Event {
        internal data object OnHomeVerticalClicked : Event()

        internal data object OnLeaderboardVerticalClicked : Event()

        internal data object OnProfileVerticalClicked : Event()

        internal data class OnNewRouteReceived(val route: ScreenRoute?) : Event()
    }

    @Immutable
    sealed class Effect

    companion object {
        private const val HOME_INDEX = 0
        private const val LEADERBOARD_INDEX = 1
        private const val PROFILE_INDEX = 2
    }
}
