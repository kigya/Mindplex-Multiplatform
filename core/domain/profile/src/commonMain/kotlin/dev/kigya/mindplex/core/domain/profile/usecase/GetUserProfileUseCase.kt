package dev.kigya.mindplex.core.domain.profile.usecase

import dev.kigya.mindplex.core.domain.connectivity.contract.ConnectivityRepositoryContract
import dev.kigya.mindplex.core.domain.interactor.base.BaseSuspendUseCase
import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.core.domain.interactor.model.MindplexDomainError
import dev.kigya.mindplex.core.domain.profile.contract.UserProfileDatabaseRepositoryContract
import dev.kigya.mindplex.core.domain.profile.contract.UserProfileNetworkRepositoryContract
import dev.kigya.mindplex.core.domain.profile.model.UserProfileDomainModel
import dev.kigya.mindplex.feature.login.domain.contract.SignInPreferencesRepositoryContract
import dev.kigya.outcome.Outcome
import dev.kigya.outcome.unwrap
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class GetUserProfileUseCase(
    private val userProfileNetworkRepositoryContract: UserProfileNetworkRepositoryContract,
    private val userProfileDatabaseRepositoryContract: UserProfileDatabaseRepositoryContract,
    private val signInPreferencesRepositoryContract: SignInPreferencesRepositoryContract,
    private val connectivityRepositoryContract: ConnectivityRepositoryContract,
) : BaseSuspendUseCase<Outcome<MindplexDomainError, UserProfileDomainModel>, None>() {
    override suspend operator fun invoke(
        params: None,
    ): Outcome<MindplexDomainError, UserProfileDomainModel> {
        return signInPreferencesRepositoryContract.userId.map { userId ->
            val userId = userId ?: return@map Outcome.failure(MindplexDomainError.OTHER)

            userProfileNetworkRepositoryContract.getUserProfile(userId).unwrap(
                onSuccess = { networkProfile ->
                    userProfileDatabaseRepositoryContract.saveUserProfile(
                        userId = userId,
                        profile = networkProfile,
                    )
                    Outcome.success(networkProfile)
                },
                onFailure = {
                    userProfileDatabaseRepositoryContract.getUserProfile(userId).unwrap(
                        onSuccess = { databaseProfile -> Outcome.success(databaseProfile) },
                        onFailure = {
                            if (!connectivityRepositoryContract.isConnected()) {
                                return@map Outcome.failure(MindplexDomainError.NETWORK)
                            }
                            return@map Outcome.failure(MindplexDomainError.OTHER)
                        },
                    )
                },
            )
        }.first()
    }
}
