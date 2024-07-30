package dev.kigya.mindplex.feature.home.presentation.ui

import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.core.domain.profile.usecase.GetUserProfileUseCase
import dev.kigya.mindplex.core.presentation.feature.BaseViewModel
import dev.kigya.mindplex.core.presentation.feature.mapper.toStubErrorType
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract

class HomeScreenViewModel(
    private val getUserProfileUseCase: GetUserProfileUseCase,
) : BaseViewModel<HomeContract.State, HomeContract.Effect>(HomeContract.State()), HomeContract {

    override fun handleEvent(event: HomeContract.Event) = withUseCaseScope {
        when (event) {
            is HomeContract.Event.OnFirstLaunch -> handleFirstLaunch()
            HomeContract.Event.OnProfilePictureLoaded -> handleProfilePictureLoaded()
            HomeContract.Event.OnProfilePictureErrorReceived -> handleProfilePictureErrorReceived()
            HomeContract.Event.OnErrorStubClicked -> handleOnErrorStubClicked()
        }
    }

    private suspend fun handleFirstLaunch() = fetchScreenData()

    private fun handleProfilePictureLoaded() = updateState {
        copy(
            isProfileNameLoading = false,
            isProfilePictureLoading = false,
        )
    }

    private fun handleProfilePictureErrorReceived() = updateState {
        copy(
            isProfileNameLoading = false,
            isProfilePictureLoading = false,
        )
    }

    private suspend fun handleOnErrorStubClicked() = fetchScreenData()

    private suspend fun fetchScreenData() {
        updateState {
            copy(
                isProfileNameLoading = true,
                isProfilePictureLoading = true,
            )
        }
        getUserProfileUseCase(None).fold(
            ifLeft = { error ->
                updateState {
                    copy(
                        stubErrorType = error.toStubErrorType(),
                        isProfileNameLoading = false,
                        isProfilePictureLoading = false,
                    )
                }
            },
            ifRight = {
                updateState {
                    copy(
                        stubErrorType = null,
                        userName = it.displayName,
                        avatarUrl = it.profilePictureUrl,
                        isProfileNameLoading = false,
                    )
                }
            },
        )
    }
}
