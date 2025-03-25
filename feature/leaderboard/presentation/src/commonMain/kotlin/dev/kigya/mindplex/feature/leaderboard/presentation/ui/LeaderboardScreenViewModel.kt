package dev.kigya.mindplex.feature.leaderboard.presentation.ui

import dev.kigya.mindplex.core.presentation.feature.BaseViewModel
import dev.kigya.mindplex.feature.leaderboard.presentation.contract.LeaderboardContract
import dev.kigya.mindplex.navigation.navigator.navigator.MindplexNavigatorContract

class LeaderboardScreenViewModel(
    navigatorContract: MindplexNavigatorContract,
) : BaseViewModel<LeaderboardContract.State, LeaderboardContract.Effect>(
    navigatorContract = navigatorContract,
    initialState = LeaderboardContract.State(),
),
    LeaderboardContract {

    override fun executeStartAction() {
        withUseCaseScope {
            fetchScreenData()
        }
    }

    override fun handleEvent(event: LeaderboardContract.Event) {
        withUseCaseScope {
            event.run {
                when (this) {
                    LeaderboardContract.Event.OnErrorStubClicked -> handleErrorStubClick()
                    LeaderboardContract.Event.OnProfilePictureErrorReceived -> handleProfilePictureError()
                    LeaderboardContract.Event.OnProfilePictureLoaded -> handleProfilePictureLoading()
                }
            }
        }
    }

    private fun handleProfilePictureLoading() = updateState {
        copy(podiumData = podiumData.copy(arePodiumLoading = false))
    }

    private fun handleProfilePictureError() = updateState {
        copy(podiumData = podiumData.copy(arePodiumLoading = false))
    }

    private suspend fun handleErrorStubClick() = fetchScreenData()

    private suspend fun fetchScreenData() {
        updateState { LeaderboardContract.State() }
    }
}
