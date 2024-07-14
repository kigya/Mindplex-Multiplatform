package dev.kigya.mindplex.feature.login.domain.usecase

import androidx.annotation.CheckResult
import dev.kigya.mindplex.core.domain.connectivity.contract.ConnectivityRepositoryContract
import dev.kigya.mindplex.core.domain.interactor.base.BaseSuspendUseCase
import dev.kigya.mindplex.feature.login.domain.contract.JwtHandlerContract
import dev.kigya.mindplex.feature.login.domain.contract.SignInNetworkRepositoryContract
import dev.kigya.mindplex.feature.login.domain.contract.SignInPreferencesRepositoryContract
import dev.kigya.mindplex.feature.login.domain.model.GoogleSignInDomainResult
import dev.kigya.mindplex.feature.login.domain.model.GoogleSignInDomainResult.Failure
import dev.kigya.mindplex.feature.login.domain.model.GoogleSignInDomainResult.GoogleSignInDomainFailureReason
import dev.kigya.mindplex.feature.login.domain.model.GoogleSignInDomainResult.Success
import dev.kigya.mindplex.feature.login.domain.model.GoogleUserDomainModel

class SignInUseCase(
    private val signInPreferencesRepositoryContract: SignInPreferencesRepositoryContract,
    private val signInNetworkRepositoryContract: SignInNetworkRepositoryContract,
    private val jwtHandlerContract: JwtHandlerContract,
    private val connectivityRepositoryContract: ConnectivityRepositoryContract,
) : BaseSuspendUseCase<GoogleSignInDomainResult, GoogleUserDomainModel?>() {

    @CheckResult
    override suspend operator fun invoke(params: GoogleUserDomainModel?): GoogleSignInDomainResult =
        params?.let { googleUser ->
            val userIdResult = jwtHandlerContract.decodeSubject(googleUser.tokenId)
            userIdResult.fold(
                onSuccess = { userId ->
                    val user = googleUser.copy(tokenId = userId)
                    signInPreferencesRepositoryContract.signIn(user.tokenId)
                    signInNetworkRepositoryContract.signIn(user)
                    Success
                },
                onFailure = { Failure(GoogleSignInDomainFailureReason.OTHER) },
            )
        } ?: run {
            if (connectivityRepositoryContract.isConnected()) {
                Failure(GoogleSignInDomainFailureReason.OTHER)
            } else {
                Failure(GoogleSignInDomainFailureReason.NETWORK)
            }
        }
}
