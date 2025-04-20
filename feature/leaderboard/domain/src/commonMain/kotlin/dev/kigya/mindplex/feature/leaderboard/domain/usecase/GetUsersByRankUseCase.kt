package dev.kigya.mindplex.feature.leaderboard.domain.usecase

import dev.kigya.mindplex.core.domain.connectivity.contract.ConnectivityRepositoryContract
import dev.kigya.mindplex.core.domain.interactor.base.BaseSuspendUseCase
import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.core.domain.interactor.model.MindplexDomainError
import dev.kigya.mindplex.feature.leaderboard.domain.contract.UserRankDatabaseRepositoryContract
import dev.kigya.mindplex.feature.leaderboard.domain.contract.UserRankNetworkRepositoryContract
import dev.kigya.mindplex.feature.leaderboard.domain.model.UserRankDomainModel
import dev.kigya.outcome.Outcome
import dev.kigya.outcome.unwrap

private const val USERS_LIMIT = 10

class GetUsersByRankUseCase(
    private val userRankNetworkRepositoryContract: UserRankNetworkRepositoryContract,
    private val userRankDatabaseRepositoryContract: UserRankDatabaseRepositoryContract,
    private val connectivityRepositoryContract: ConnectivityRepositoryContract,
) : BaseSuspendUseCase<Outcome<MindplexDomainError, List<UserRankDomainModel>>, None>() {

    override suspend operator fun invoke(
        params: None,
    ): Outcome<MindplexDomainError, List<UserRankDomainModel>> = userRankNetworkRepositoryContract
        .getTopUsersByScore(USERS_LIMIT)
        .unwrap(
            onFailure = {
                userRankDatabaseRepositoryContract.getTopUsersByScore(USERS_LIMIT).unwrap(
                    onSuccess = { Outcome.success(it) },
                    onFailure = {
                        if (!connectivityRepositoryContract.isConnected()) {
                            Outcome.failure(MindplexDomainError.NETWORK)
                        }
                        Outcome.failure(MindplexDomainError.OTHER)
                    },
                )
            },
            onSuccess = { networkUsers ->
                userRankDatabaseRepositoryContract.saveUsers(networkUsers)
                Outcome.success(networkUsers)
            },
        )
}
