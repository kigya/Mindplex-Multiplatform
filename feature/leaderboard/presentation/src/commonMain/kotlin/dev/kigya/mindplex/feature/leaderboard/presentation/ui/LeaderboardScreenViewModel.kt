package dev.kigya.mindplex.feature.leaderboard.presentation.ui

import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.core.presentation.feature.BaseViewModel
import dev.kigya.mindplex.core.presentation.feature.mapper.toStubErrorType
import dev.kigya.mindplex.core.presentation.uikit.StubErrorType
import dev.kigya.mindplex.feature.leaderboard.domain.usecase.GetUsersByRankUseCase
import dev.kigya.mindplex.feature.leaderboard.presentation.contract.LeaderboardContract
import dev.kigya.mindplex.feature.leaderboard.presentation.mapper.UserCardMapper
import dev.kigya.mindplex.navigation.navigator.navigator.MindplexNavigatorContract
import dev.kigya.outcome.unwrap
import kotlinx.collections.immutable.toImmutableList

class LeaderboardScreenViewModel(
    navigatorContract: MindplexNavigatorContract,
    private val getUserRankUseCase: GetUsersByRankUseCase,
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
                }
            }
        }
    }

    private fun handleErrorStubClick() = fetchScreenData()

    private fun fetchScreenData() = withUseCaseScope {
        updateState { LeaderboardContract.State() }

        getUserRankUseCase(None).unwrap(
            onSuccess = { userRanks ->
                if (userRanks.isEmpty()) {
                    updateState { copy(stubErrorType = StubErrorType.NETWORK) }
                    return@unwrap
                }

                val podiumUsers = userRanks.take(LeaderboardContract.PODIUM_SIZE)
                    .mapIndexed { index, user ->
                        UserCardMapper.mapToDomainModel(user)
                            .copy(userRank = (index + 1).toString())
                    }
                    .toImmutableList()

                val nonPodiumUsers = userRanks
                    .drop(LeaderboardContract.PODIUM_SIZE)
                    .take(LeaderboardContract.NON_PODIUM_SIZE)
                    .mapIndexed { index, user ->
                        UserCardMapper.mapToDomainModel(user)
                            .copy(userRank = (index + 4).toString())
                    }
                    .toImmutableList()

                updateState {
                    copy(
                        stubErrorType = null,
                        podiumUsers = podiumUsers,
                        nonPodiumUsers = nonPodiumUsers,
                        isLoading = false,
                    )
                }
            },
            onFailure = { error ->
                updateState {
                    copy(stubErrorType = error.toStubErrorType())
                }
            },
        )
    }
}
