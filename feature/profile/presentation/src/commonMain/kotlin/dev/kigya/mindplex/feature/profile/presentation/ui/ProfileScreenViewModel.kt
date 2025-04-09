package dev.kigya.mindplex.feature.profile.presentation.ui

import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.core.presentation.feature.BaseViewModel
import dev.kigya.mindplex.core.presentation.feature.mapper.toStubErrorType
import dev.kigya.mindplex.feature.profile.domain.usecase.GetProfileUseCase
import dev.kigya.mindplex.feature.profile.domain.usecase.GetThemeUseCase
import dev.kigya.mindplex.feature.profile.domain.usecase.SaveThemeUseCase
import dev.kigya.mindplex.feature.profile.presentation.contract.ProfileContract
import dev.kigya.mindplex.navigation.navigator.navigator.MindplexNavigatorContract
import kotlinx.coroutines.supervisorScope

class ProfileScreenViewModel(
    navigatorContract: MindplexNavigatorContract,
    private val getUserProfileUseCase: GetProfileUseCase,
    private val getThemeUseCase: GetThemeUseCase,
    private val saveThemeUseCase: SaveThemeUseCase,
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
                }
            }
        }
    }

    private suspend fun handleErrorStubClick() = fetchScreenData()

    private suspend fun fetchScreenData() = supervisorScope {
        updateState { ProfileContract.State() }

        getUserProfileUseCase(None).fold(
            ifRight = { userProfile ->
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
            ifLeft = { error ->
                updateState {
                    copy(
                        stubErrorType = error.toStubErrorType(),
                    )
                }
            },
        )
    }

    private suspend fun fetchTheme() {
        getThemeUseCase(None).fold(
            ifRight = { theme ->
                updateState { copy(isDarkTheme = theme) }
            },
            ifLeft = { error ->
                updateState {
                    copy(
                        stubErrorType = error.toStubErrorType(),
                    )
                }
            },
        )
    }

    private suspend fun handleThemeChange(isDarkTheme: Boolean) {
        saveThemeUseCase.invoke(isDarkTheme)
        updateState { copy(isDarkTheme = isDarkTheme) }
    }
}
