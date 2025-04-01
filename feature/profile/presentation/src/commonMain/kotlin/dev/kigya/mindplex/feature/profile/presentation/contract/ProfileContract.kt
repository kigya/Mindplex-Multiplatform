package dev.kigya.mindplex.feature.profile.presentation.contract

import androidx.compose.runtime.Immutable
import dev.kigya.mindplex.core.presentation.feature.CopyableComponentState
import dev.kigya.mindplex.core.presentation.feature.UnidirectionalViewModelContract
import dev.kigya.mindplex.core.presentation.uikit.StubErrorType
import dev.kigya.mindplex.core.util.extension.empty

interface ProfileContract :
    UnidirectionalViewModelContract<ProfileContract.State, ProfileContract.Event, ProfileContract.Effect> {
    @Immutable
    data class State internal constructor(
        val stubErrorType: StubErrorType? = null,
        val profileLoading: ProfileScreenLoadingData = ProfileScreenLoadingData(),
        val userProfile: UserProfile = UserProfile(),
    ) : CopyableComponentState {

        @ConsistentCopyVisibility
        @Immutable
        data class UserProfile internal constructor(
            val userName: String = String.empty,
            val avatarUrl: String? = null,
            val userCountry: String? = null,
            val userScore: String = String.empty,
            val userLocalRank: String = String.empty,
            val userGlobalRank: String = String.empty,
        )

        @ConsistentCopyVisibility
        @Immutable
        data class ProfileScreenLoadingData internal constructor(
            val isProfileLoading: Boolean = true,
        )
    }

    @Immutable
    sealed class Event {

        internal data object OnErrorStubClicked : Event()

        internal data object OnProfileLoaded : Event()
    }

    @Immutable
    sealed class Effect
}
