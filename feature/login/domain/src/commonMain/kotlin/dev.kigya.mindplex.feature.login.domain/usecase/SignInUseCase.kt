package dev.kigya.mindplex.feature.login.domain.usecase

import dev.kigya.mindplex.core.domain.connectivity.contract.ConnectivityRepositoryContract
import dev.kigya.mindplex.core.domain.interactor.base.BaseSuspendUseCase
import dev.kigya.mindplex.feature.login.domain.contract.SignInPreferencesRepositoryContract
import dev.kigya.mindplex.feature.login.domain.model.GoogleSignInDomainResult
import dev.kigya.mindplex.feature.login.domain.model.GoogleSignInDomainResult.Failure
import dev.kigya.mindplex.feature.login.domain.model.GoogleSignInDomainResult.GoogleSignInDomainFailureReason
import dev.kigya.mindplex.feature.login.domain.model.GoogleSignInDomainResult.Success
import dev.kigya.mindplex.feature.login.domain.model.GoogleUserDomainModel

class SignInUseCase(
    private val signInPreferencesRepositoryContract: SignInPreferencesRepositoryContract,
    private val connectivityRepositoryContract: ConnectivityRepositoryContract,
) : BaseSuspendUseCase<GoogleSignInDomainResult, GoogleUserDomainModel?>() {
    override suspend operator fun invoke(params: GoogleUserDomainModel?): GoogleSignInDomainResult =
        params?.let {
            signInPreferencesRepositoryContract.signIn(params.tokenId)
            Success
        } ?: run {
            if (connectivityRepositoryContract.isConnected()) {
                Failure(GoogleSignInDomainFailureReason.OTHER)
            } else {
                Failure(GoogleSignInDomainFailureReason.NETWORK)
            }
        }
}
