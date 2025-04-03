package dev.kigya.mindplex.feature.leaderboard.presentation.ui

import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.core.presentation.feature.BaseViewModel
import dev.kigya.mindplex.core.presentation.feature.mapper.toStubErrorType
import dev.kigya.mindplex.core.presentation.uikit.StubErrorType
import dev.kigya.mindplex.feature.leaderboard.domain.usecase.GetUsersByRankUseCase
import dev.kigya.mindplex.feature.leaderboard.presentation.contract.LeaderboardContract
import dev.kigya.mindplex.feature.leaderboard.presentation.mapper.UserCardMapper
import dev.kigya.mindplex.navigation.navigator.navigator.MindplexNavigatorContract
import kotlinx.collections.immutable.toImmutableList

private const val NON_PODIUM_USER_LIMIT = 7

class LeaderboardScreenViewModel(
    navigatorContract: MindplexNavigatorContract,
    private val getUserPlaceUseCase: GetUsersByRankUseCase,
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

        getUserPlaceUseCase(None).fold(
            ifRight = { userPlaces ->
                if (userPlaces.isEmpty()) {
                    updateState { copy(stubErrorType = StubErrorType.NETWORK) }
                    return@fold
                }

                val userCardMapper = UserCardMapper

                val podiumUsers = userPlaces.take(LeaderboardContract.PODIUM_SIZE)
                    .mapIndexed { index, user ->
                        userCardMapper.mapToDomainModel(user).copy(userPlace = (index + 1).toString())
                    }
                    .toImmutableList()

                val nonPodiumUsers = userPlaces
                    .drop(LeaderboardContract.PODIUM_SIZE)
                    .take(NON_PODIUM_USER_LIMIT)
                    .mapIndexed { index, user ->
                        userCardMapper.mapToDomainModel(user).copy(userPlace = (index + 4).toString())
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
            ifLeft = { error ->
                updateState {
                    copy(stubErrorType = error.toStubErrorType())
                }
            },
        )
    }
}
