package dev.kigya.mindplex.feature.profile.presentation.ui

import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.core.domain.profile.usecase.CheckAppInDarkThemeUseCase
import dev.kigya.mindplex.core.domain.profile.usecase.GetUserProfileUseCase
import dev.kigya.mindplex.core.domain.profile.usecase.UpdateAppInDarkThemeUsrCase
import dev.kigya.mindplex.core.presentation.feature.BaseViewModel
import dev.kigya.mindplex.core.presentation.feature.mapper.toStubErrorType
import dev.kigya.mindplex.feature.login.domain.usecase.SignOutUseCase
import dev.kigya.mindplex.feature.profile.presentation.contract.ProfileContract
import dev.kigya.mindplex.navigation.navigator.navigator.MindplexNavigatorContract
import dev.kigya.mindplex.navigation.navigator.route.ScreenRoute
import dev.kigya.outcome.unwrap
import kotlinx.coroutines.supervisorScope

class ProfileScreenViewModel(
    navigatorContract: MindplexNavigatorContract,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getThemeUseCase: CheckAppInDarkThemeUseCase,
    private val saveThemeUseCase: UpdateAppInDarkThemeUsrCase,
    private val singOutUseCase: SignOutUseCase,
) : BaseViewModel<ProfileContract.State, ProfileContract.Effect>(
    navigatorContract = navigatorContract,
    initialState = ProfileContract.State(),
),
    ProfileContract {

    override fun executeStartAction() {
        withUseCaseScope {
            fetchScreenData()
            fetchTheme()
        }
    }

    override fun handleEvent(event: ProfileContract.Event) {
        withUseCaseScope {
            event.run {
                when (this) {
                    is ProfileContract.Event.OnErrorStubClicked -> handleErrorStubClick()
                    is ProfileContract.Event.OnThemeChanged -> handleThemeChange(isDarkTheme)
                    is ProfileContract.Event.GoToRegistration -> handleGoToRegistration()
                }
            }
        }
    }

    private suspend fun handleGoToRegistration() {
        singOutUseCase(None)
        navigatorContract.navigateTo(
            route = ScreenRoute.Login,
            popUpToRoute = ScreenRoute.Home,
            inclusive = true,
        )
    }

    private suspend fun handleErrorStubClick() = fetchScreenData()

    private suspend fun fetchScreenData() = supervisorScope {
        updateState { ProfileContract.State() }

        getUserProfileUseCase(None).unwrap(
            onSuccess = { userProfile ->
                updateState {
                    copy(
                        stubErrorType = null,
                        profileLoading = false,
                        userProfile = ProfileContract.State.UserProfile(
                            userName = userProfile.displayName,
                            avatarUrl = userProfile.profilePictureUrl,
                            userCountry = userProfile.userCountry,
                            userScore = userProfile.score.toString(),
                            userGlobalRank = userProfile.globalRank.toString(),
                            userLocalRank = userProfile.localRank.toString(),
                        ),
                    )
                }
            },
            onFailure = { error ->
                updateState {
                    copy(
                        stubErrorType = error.toStubErrorType(),
                    )
                }
            },
        )
    }

    private suspend fun handleThemeChange(isDarkTheme: Boolean) {
        saveThemeUseCase(isDarkTheme)
        updateState { copy(isDarkTheme = isDarkTheme) }
    }

    private suspend fun fetchTheme() {
        getThemeUseCase(None).unwrap(
            onSuccess = { theme ->
                updateState { copy(isDarkTheme = theme) }
            },
            onFailure = { error ->
                updateState {
                    copy(stubErrorType = error.toStubErrorType())
                }
            },
        )
    }
}
