package dev.kigya.mindplex.feature.login.presentation.contract

import androidx.compose.runtime.Immutable
import com.mmk.kmpauth.google.GoogleUser
import dev.kigya.mindplex.core.presentation.feature.CopyableComponentState
import dev.kigya.mindplex.core.presentation.feature.UnidirectionalViewModelContract
import dev.kigya.mindplex.core.presentation.uikit.StubErrorType

interface LoginContract :
    UnidirectionalViewModelContract<LoginContract.State, LoginContract.Event, LoginContract.Effect> {
    @Immutable
    data class State internal constructor(
        val stubErrorType: StubErrorType? = null,
    ) : CopyableComponentState

    @Immutable
    sealed class Event {

        internal data class OnGoogleSignInResultReceived(val googleUser: GoogleUser?) : Event()

        internal data object OnErrorStubClicked : Event()
    }

    @Immutable
    sealed class Effect
}
