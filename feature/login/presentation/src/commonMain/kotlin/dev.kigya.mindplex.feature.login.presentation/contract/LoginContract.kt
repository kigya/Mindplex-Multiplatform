package dev.kigya.mindplex.feature.login.presentation.contract

import androidx.compose.runtime.Immutable
import com.mmk.kmpauth.google.GoogleUser
import dev.kigya.mindplex.core.presentation.component.StubErrorType
import dev.kigya.mindplex.core.presentation.feature.CopyableComponentState
import dev.kigya.mindplex.core.presentation.feature.UnidirectionalViewModelContract

interface LoginContract :
    UnidirectionalViewModelContract<LoginContract.State, LoginContract.Event, LoginContract.Effect> {
    @Immutable
    data class State(
        val stubErrorType: StubErrorType? = null,
    ) : CopyableComponentState

    @Immutable
    sealed class Event {
        data object OnFirstLaunch : Event()
        data class OnGoogleSignInResultReceived(val googleUser: GoogleUser?) : Event()
        data object OnErrorStubClicked : Event()
    }

    @Immutable
    sealed class Effect {
        data object CreateNewCredentialRequest : Effect()
        data object ShowSomethingWentWrongStub : Effect()
    }
}
