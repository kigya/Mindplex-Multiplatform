package dev.kigya.mindplex.feature.login.domain.usecase

import androidx.annotation.CheckResult
import dev.kigya.mindplex.core.domain.connectivity.contract.ConnectivityRepositoryContract
import dev.kigya.mindplex.core.domain.interactor.base.BaseSuspendUseCase
import dev.kigya.mindplex.core.domain.interactor.model.MindplexDomainError
import dev.kigya.mindplex.feature.login.domain.contract.ProfileImageInterceptorContract
import dev.kigya.mindplex.feature.login.domain.contract.SignInNetworkRepositoryContract
import dev.kigya.mindplex.feature.login.domain.contract.SignInPreferencesRepositoryContract
import dev.kigya.mindplex.feature.login.domain.model.GoogleUserSignInDomainModel
import dev.kigya.outcome.Outcome

class SignInUseCase(
    private val signInPreferencesRepositoryContract: SignInPreferencesRepositoryContract,
    private val signInNetworkRepositoryContract: SignInNetworkRepositoryContract,
    private val connectivityRepositoryContract: ConnectivityRepositoryContract,
    private val profileImageInterceptorContract: ProfileImageInterceptorContract,
) : BaseSuspendUseCase<Outcome<MindplexDomainError, String>, GoogleUserSignInDomainModel?>() {

    @CheckResult
    override suspend fun invoke(
        params: GoogleUserSignInDomainModel?,
    ): Outcome<MindplexDomainError, String> {
        if (!connectivityRepositoryContract.isConnected()) {
            return Outcome.failure(MindplexDomainError.NETWORK)
        }

        val googleUserDomainModel = params ?: return Outcome.failure(MindplexDomainError.OTHER)

        val interceptedUser = googleUserDomainModel.copy(
            profilePictureUrl = profileImageInterceptorContract.intercept(
                googleUserDomainModel.profilePictureUrl,
            ),
        )

        return signInNetworkRepositoryContract.signIn(interceptedUser)
            .fold(
                onSuccess = { userId ->
                    signInPreferencesRepositoryContract.signIn(userId)
                    Outcome.success(userId)
                },
                onFailure = {
                    Outcome.failure(MindplexDomainError.OTHER)
                },
            )
    }
}
