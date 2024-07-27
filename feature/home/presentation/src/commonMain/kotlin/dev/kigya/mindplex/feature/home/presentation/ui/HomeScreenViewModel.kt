package dev.kigya.mindplex.feature.home.presentation.ui

import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.core.domain.profile.usecase.GetUserProfileUseCase
import dev.kigya.mindplex.core.presentation.feature.BaseViewModel
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract

class HomeScreenViewModel(
    private val getUserProfileUseCase: GetUserProfileUseCase,
) : BaseViewModel<HomeContract.State, HomeContract.Effect>(HomeContract.State()), HomeContract {

    override fun handleEvent(event: HomeContract.Event) = withUseCaseScope {
        when (event) {
            is HomeContract.Event.OnFirstLaunch -> handleFirstLaunch()
        }
    }

    private suspend fun handleFirstLaunch() {
        updateState { copy(isProfileLoading = true) }
        getUserProfileUseCase(None).fold(
            ifLeft = {
                updateState {
                    copy(isProfileLoading = false)
                }
            },
            ifRight = {
                updateState {
                    copy(
                        userName = it.displayName,
                        avatarUrl = it.profilePictureUrl,
                        isProfileLoading = false,
                    )
                }
            },
        )
    }
}
