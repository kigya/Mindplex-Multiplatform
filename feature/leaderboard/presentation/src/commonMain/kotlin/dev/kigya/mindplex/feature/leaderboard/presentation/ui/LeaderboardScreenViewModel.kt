package dev.kigya.mindplex.feature.leaderboard.presentation.ui

import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.core.presentation.feature.BaseViewModel
import dev.kigya.mindplex.core.presentation.feature.mapper.toStubErrorType
import dev.kigya.mindplex.feature.leaderboard.domain.usecase.GetUserPlaceUseCase
import dev.kigya.mindplex.feature.leaderboard.presentation.contract.LeaderboardContract
import dev.kigya.mindplex.navigation.navigator.navigator.MindplexNavigatorContract
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.supervisorScope

class LeaderboardScreenViewModel(
    navigatorContract: MindplexNavigatorContract,
    private val getUserPlaceUseCase: GetUserPlaceUseCase,
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

    private suspend fun fetchScreenData() = supervisorScope {
        updateState { LeaderboardContract.State() }

        getUserPlaceUseCase.invoke(None).fold(
            ifRight = { userPlaces ->
                updateState {
                    copy(
                        stubErrorType = null,
                        podiumData = podiumData.copy(
                            arePodiumLoading = false,
                            place = userPlaces.take(LeaderboardContract.PLACE_AMOUNT)
                                .map { it.displayName }
                                .toImmutableList(),
                        ),
                        userCardData = userPlaces.mapIndexed { index, user ->
                            LeaderboardContract.State.UserCardData(
                                userName = user.displayName,
                                userScore = user.score.toString(),
                                avatarUrl = user.profilePictureUrl,
                                userPlace = (index + 1).toString(),
                                isProfileNameLoading = false,
                                isProfilePictureLoading = false,
                            )
                        },
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
}
