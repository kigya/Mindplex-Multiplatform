package dev.kigya.mindplex.feature.profile.domain.usecase

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import dev.kigya.mindplex.core.domain.connectivity.contract.ConnectivityRepositoryContract
import dev.kigya.mindplex.core.domain.interactor.base.BaseSuspendUseCase
import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.core.domain.interactor.model.MindplexDomainError
import dev.kigya.mindplex.feature.login.domain.contract.SignInPreferencesRepositoryContract
import dev.kigya.mindplex.feature.profile.domain.contract.ProfileDatabaseRepositoryContract
import dev.kigya.mindplex.feature.profile.domain.contract.ProfileNetworkRepositoryContract
import dev.kigya.mindplex.feature.profile.domain.model.UserProfileDomainModel
import kotlinx.coroutines.flow.firstOrNull

class GetProfileUseCase(
    private val userProfileNetworkRepository: ProfileNetworkRepositoryContract,
    private val userProfileDatabaseRepository: ProfileDatabaseRepositoryContract,
    private val connectivityRepository: ConnectivityRepositoryContract,
    private val signInPreferencesRepositoryContract: SignInPreferencesRepositoryContract,
) : BaseSuspendUseCase<Either<MindplexDomainError, UserProfileDomainModel>, None>() {

    override suspend fun invoke(
        params: None,
    ): Either<MindplexDomainError, UserProfileDomainModel> = either {
        val token = signInPreferencesRepositoryContract.userToken.firstOrNull()
        ensure(!token.isNullOrBlank()) { MindplexDomainError.OTHER }

        userProfileNetworkRepository.getTopUsersByToken(token)
            .fold(
                onSuccess = { networkUser ->
                    userProfileDatabaseRepository.saveUser(networkUser, token)
                    networkUser
                },
                onFailure = {
                    val cachedUser = userProfileDatabaseRepository.getTopUsersByToken(token)
                        .getOrElse {
                            ensure(
                                connectivityRepository.isConnected().not(),
                            ) { MindplexDomainError.OTHER }
                            raise(MindplexDomainError.NETWORK)
                        }
                    cachedUser
                },
            )
    }
}
