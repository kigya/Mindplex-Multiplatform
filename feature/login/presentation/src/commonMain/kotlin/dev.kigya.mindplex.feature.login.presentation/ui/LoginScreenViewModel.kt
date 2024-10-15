package dev.kigya.mindplex.feature.login.presentation.ui

import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.core.presentation.feature.BaseViewModel
import dev.kigya.mindplex.core.presentation.feature.mapper.toStubErrorType
import dev.kigya.mindplex.feature.login.domain.usecase.GetIsUserSignedInUseCase
import dev.kigya.mindplex.feature.login.domain.usecase.SignInUseCase
import dev.kigya.mindplex.feature.login.presentation.contract.LoginContract
import dev.kigya.mindplex.feature.login.presentation.mapper.toDomain
import dev.kigya.mindplex.navigation.navigator.navigator.AppNavigatorContract
import dev.kigya.mindplex.navigation.navigator.route.ScreenRoute

class LoginScreenViewModel(
    private val navigatorContract: AppNavigatorContract,
    private val signInUseCase: SignInUseCase,
    private val getIsUserSignedInUseCase: GetIsUserSignedInUseCase,
) : BaseViewModel<LoginContract.State, LoginContract.Effect>(LoginContract.State()), LoginContract {

    override fun handleEvent(event: LoginContract.Event) = withUseCaseScope {
        event.run {
            when (this) {
                is LoginContract.Event.OnFirstLaunch -> handleFirstLaunch()
                is LoginContract.Event.OnGoogleSignInResultReceived -> handleGoogleSignInResult()
                is LoginContract.Event.OnErrorStubClicked -> handleErrorStubClick()
            }
        }
    }

    private suspend fun handleFirstLaunch() {
        getIsUserSignedInUseCase(None).collect { isSignedIn ->
            if (isSignedIn) {
                navigatorContract.navigateTo(
                    route = ScreenRoute.Home,
                    popUpToRoute = ScreenRoute.Splash,
                    inclusive = true,
                )
            }
        }
    }

    private suspend fun LoginContract.Event.OnGoogleSignInResultReceived.handleGoogleSignInResult() {
        signInUseCase(googleUser?.toDomain()).fold(
            ifLeft = { error ->
                updateState {
                    copy(stubErrorType = error.toStubErrorType())
                }
            },
            ifRight = { },
        )
    }

    private fun handleErrorStubClick() = updateState { copy(stubErrorType = null) }
}
