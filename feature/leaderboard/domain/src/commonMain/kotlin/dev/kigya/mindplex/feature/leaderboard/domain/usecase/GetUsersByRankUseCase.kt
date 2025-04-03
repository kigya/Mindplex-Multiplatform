package dev.kigya.mindplex.feature.leaderboard.domain.usecase

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import dev.kigya.mindplex.core.domain.connectivity.contract.ConnectivityRepositoryContract
import dev.kigya.mindplex.core.domain.interactor.base.BaseSuspendUseCase
import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.core.domain.interactor.model.MindplexDomainError
import dev.kigya.mindplex.feature.leaderboard.domain.contract.UserRankDatabaseRepositoryContract
import dev.kigya.mindplex.feature.leaderboard.domain.contract.UserRankNetworkRepositoryContract
import dev.kigya.mindplex.feature.leaderboard.domain.model.UserRankDomainModel

private const val USERS_LIMIT = 10

class GetUsersByRankUseCase(
    private val userRankNetworkRepositoryContract: UserRankNetworkRepositoryContract,
    private val userRankDatabaseRepositoryContract: UserRankDatabaseRepositoryContract,
    private val connectivityRepositoryContract: ConnectivityRepositoryContract,
) : BaseSuspendUseCase<Either<MindplexDomainError, List<UserRankDomainModel>>, None>() {

    override suspend operator fun invoke(
        params: None,
    ): Either<MindplexDomainError, List<UserRankDomainModel>> = either {
        userRankNetworkRepositoryContract.getTopUsersByScore(USERS_LIMIT)
            .fold(
                onSuccess = { networkUsers ->
                    userRankDatabaseRepositoryContract.saveUsers(networkUsers)
                    networkUsers
                },
                onFailure = {
                    userRankDatabaseRepositoryContract.getTopUsersByScore(USERS_LIMIT).fold(
                        onSuccess = { it },
                        onFailure = {
                            ensure(connectivityRepositoryContract.isConnected().not()) { MindplexDomainError.OTHER }
                            raise(MindplexDomainError.NETWORK)
                        },
                    )
                },
            )
    }
}
