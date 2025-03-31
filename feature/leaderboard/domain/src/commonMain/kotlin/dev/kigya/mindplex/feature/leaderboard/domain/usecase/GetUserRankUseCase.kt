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

private const val GET_USERS_LIMIT = 10

class GetUserRankUseCase(
    private val userPlaceNetworkRepositoryContract: UserRankNetworkRepositoryContract,
    private val userPlaceDatabaseRepositoryContract: UserRankDatabaseRepositoryContract,
    private val connectivityRepositoryContract: ConnectivityRepositoryContract,
) : BaseSuspendUseCase<Either<MindplexDomainError, List<UserRankDomainModel>>, None>() {

    override suspend operator fun invoke(
        params: None,
    ): Either<MindplexDomainError, List<UserRankDomainModel>> = either {
        userPlaceNetworkRepositoryContract.getTopUsersByScore(GET_USERS_LIMIT)
            .fold(
                onSuccess = { networkUsers ->
                    userPlaceDatabaseRepositoryContract.saveUsers(networkUsers)
                    networkUsers
                },
                onFailure = {
                    userPlaceDatabaseRepositoryContract.getTopUsersByScore().fold(
                        onSuccess = { databaseUsers ->
                            databaseUsers
                        },
                        onFailure = {
                            ensure(connectivityRepositoryContract.isConnected().not()) { MindplexDomainError.OTHER }
                            raise(MindplexDomainError.NETWORK)
                        },
                    )
                },
            )
    }
}
