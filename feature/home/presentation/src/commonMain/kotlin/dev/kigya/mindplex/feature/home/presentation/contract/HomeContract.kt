package dev.kigya.mindplex.feature.home.presentation.contract

import androidx.compose.runtime.Immutable
import dev.kigya.mindplex.core.presentation.component.StubErrorType
import dev.kigya.mindplex.core.presentation.feature.CopyableComponentState
import dev.kigya.mindplex.core.presentation.feature.UnidirectionalViewModelContract
import dev.kigya.mindplex.core.util.extension.empty

interface HomeContract :
    UnidirectionalViewModelContract<HomeContract.State, HomeContract.Event, HomeContract.Effect> {
    @Immutable
    data class State(
        val stubErrorType: StubErrorType? = null,
        val userName: String = String.empty,
        val avatarUrl: String? = null,
        val isProfileNameLoading: Boolean = false,
        val isProfilePictureLoading: Boolean = false,
    ) : CopyableComponentState

    @Immutable
    sealed class Event {
        data object OnFirstLaunch : Event()
        data object OnProfilePictureLoaded : Event()
        data object OnProfilePictureErrorReceived : Event()
        data object OnErrorStubClicked : Event()
    }

    @Immutable
    sealed class Effect
}
