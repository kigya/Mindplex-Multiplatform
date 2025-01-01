package dev.kigya.mindplex.feature.login.presentation.ui

import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.core.presentation.feature.BaseViewModel
import dev.kigya.mindplex.core.presentation.feature.mapper.toStubErrorType
import dev.kigya.mindplex.core.util.extension.Lambda
import dev.kigya.mindplex.feature.login.domain.usecase.GetIsUserSignedInUseCase
import dev.kigya.mindplex.feature.login.domain.usecase.SignInUseCase
import dev.kigya.mindplex.feature.login.presentation.contract.LoginContract
import dev.kigya.mindplex.feature.login.presentation.mapper.GoogleUserPresentationMapper
import dev.kigya.mindplex.feature.login.presentation.mapper.GoogleUserPresentationMapper.mappedBy
import dev.kigya.mindplex.navigation.navigator.navigator.MindplexNavigatorContract
import dev.kigya.mindplex.navigation.navigator.route.ScreenRoute

class LoginScreenViewModel(
    private val signInUseCase: SignInUseCase,
    private val getIsUserSignedInUseCase: GetIsUserSignedInUseCase,
    navigatorContract: MindplexNavigatorContract,
) : BaseViewModel<LoginContract.State, LoginContract.Effect>(
    navigatorContract = navigatorContract,
    initialState = LoginContract.State(),
),
    LoginContract {

    override fun executeStartAction() = withUseCaseScope {
        getIsUserSignedInUseCase(None).collect { isSignedIn ->
            if (isSignedIn) {
                navigatorContract.navigateTo(
                    route = ScreenRoute.Home,
                    popUpToRoute = ScreenRoute.Login,
                    inclusive = true,
                )
            }
        }
    }

    override fun handleEvent(event: LoginContract.Event) = withUseCaseScope {
        event.run {
            when (this) {
                is LoginContract.Event.OnGoogleSignInResultReceived -> handleGoogleSignInResult()
                is LoginContract.Event.OnErrorStubClicked -> handleErrorStubClick()
            }
        }
    }

    private suspend fun LoginContract.Event.OnGoogleSignInResultReceived.handleGoogleSignInResult() =
        signInUseCase(googleUser mappedBy GoogleUserPresentationMapper).fold(
            ifLeft = { error ->
                updateState {
                    copy(stubErrorType = error.toStubErrorType())
                }
            },
            ifRight = Lambda.noOpConsumer(),
        )

    private fun handleErrorStubClick() = updateState { copy(stubErrorType = null) }
}
