package dev.kigya.mindplex.feature.leaderboard.domain.usecase

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import dev.kigya.mindplex.core.domain.connectivity.contract.ConnectivityRepositoryContract
import dev.kigya.mindplex.core.domain.interactor.base.BaseSuspendUseCase
import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.core.domain.interactor.model.MindplexDomainError
import dev.kigya.mindplex.feature.leaderboard.domain.contract.UserPlaceDatabaseRepositoryContract
import dev.kigya.mindplex.feature.leaderboard.domain.contract.UserPlaceNetworkRepositoryContract
import dev.kigya.mindplex.feature.leaderboard.domain.model.UserPlaceDomainModel

class GetUserPlaceUseCase(
    private val userPlaceNetworkRepositoryContract: UserPlaceNetworkRepositoryContract,
    private val userPlaceDatabaseRepositoryContract: UserPlaceDatabaseRepositoryContract,
    private val connectivityRepositoryContract: ConnectivityRepositoryContract,
) : BaseSuspendUseCase<Either<MindplexDomainError, List<UserPlaceDomainModel>>, None>() {

    override suspend operator fun invoke(
        params: None,
    ): Either<MindplexDomainError, List<UserPlaceDomainModel>> = either {
        userPlaceNetworkRepositoryContract.getTopUsersByScore().fold(
            onSuccess = { networkUsers ->
                networkUsers
            },
            onFailure = {
                userPlaceDatabaseRepositoryContract.getTopUsersByScore().fold(
                    onSuccess = { databaseUsers ->
                        databaseUsers
                    },
                    onFailure = {
                        ensure(
                            connectivityRepositoryContract.isConnected().not(),
                        ) { MindplexDomainError.OTHER }
                        raise(MindplexDomainError.NETWORK)
                    },
                )
            },
        )
    }
}
