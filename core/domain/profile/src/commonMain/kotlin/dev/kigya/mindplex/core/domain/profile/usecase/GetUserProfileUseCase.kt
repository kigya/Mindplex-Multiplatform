package dev.kigya.mindplex.core.domain.profile.usecase

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import dev.kigya.mindplex.core.domain.connectivity.contract.ConnectivityRepositoryContract
import dev.kigya.mindplex.core.domain.interactor.base.BaseSuspendUseCase
import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.core.domain.interactor.model.MindplexDomainError
import dev.kigya.mindplex.core.domain.profile.contract.UserProfileNetworkRepositoryContract
import dev.kigya.mindplex.core.domain.profile.model.UserProfileDomainModel
import dev.kigya.mindplex.core.util.dsl.requireNotNullOrRaise
import dev.kigya.mindplex.feature.login.domain.contract.SignInPreferencesRepositoryContract
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class GetUserProfileUseCase(
    private val userProfileNetworkRepositoryContract: UserProfileNetworkRepositoryContract,
    private val signInPreferencesRepositoryContract: SignInPreferencesRepositoryContract,
    private val connectivityRepositoryContract: ConnectivityRepositoryContract,
) : BaseSuspendUseCase<Either<MindplexDomainError, UserProfileDomainModel>, None>() {
    override suspend operator fun invoke(
        params: None,
    ): Either<MindplexDomainError, UserProfileDomainModel> = either {
        signInPreferencesRepositoryContract.userToken.map { token ->
            requireNotNullOrRaise(token) { raise(MindplexDomainError.OTHER) }

            userProfileNetworkRepositoryContract.getUserProfile(token).fold(
                onSuccess = { it },
                onFailure = {
                    ensure(connectivityRepositoryContract.isConnected().not()) { MindplexDomainError.OTHER }
                    raise(MindplexDomainError.NETWORK)
                },
            )
        }.first()
    }
}
