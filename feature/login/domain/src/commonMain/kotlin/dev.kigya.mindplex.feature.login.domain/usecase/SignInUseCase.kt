package dev.kigya.mindplex.feature.login.domain.usecase

import androidx.annotation.CheckResult
import dev.kigya.mindplex.core.domain.connectivity.contract.ConnectivityRepositoryContract
import dev.kigya.mindplex.core.domain.interactor.base.BaseSuspendUseCase
import dev.kigya.mindplex.core.domain.interactor.model.MindplexDomainError
import dev.kigya.mindplex.feature.login.domain.contract.CountryCodeNetworkRepositoryContract
import dev.kigya.mindplex.feature.login.domain.contract.JwtHandlerContract
import dev.kigya.mindplex.feature.login.domain.contract.ProfileImageInterceptorContract
import dev.kigya.mindplex.feature.login.domain.contract.SignInNetworkRepositoryContract
import dev.kigya.mindplex.feature.login.domain.contract.SignInPreferencesRepositoryContract
import dev.kigya.mindplex.feature.login.domain.model.GoogleUserSignInDomainModel
import dev.kigya.outcome.Outcome
import dev.kigya.outcome.getOrNull
import dev.kigya.outcome.unwrap

class SignInUseCase(
    private val signInPreferencesRepositoryContract: SignInPreferencesRepositoryContract,
    private val signInNetworkRepositoryContract: SignInNetworkRepositoryContract,
    private val countryCodeRepositoryContract: CountryCodeNetworkRepositoryContract,
    private val connectivityRepositoryContract: ConnectivityRepositoryContract,
    private val profileImageInterceptorContract: ProfileImageInterceptorContract,
    private val jwtHandlerContract: JwtHandlerContract,
) : BaseSuspendUseCase<Outcome<MindplexDomainError, Unit>, GoogleUserSignInDomainModel?>() {

    @CheckResult
    override suspend fun invoke(
        params: GoogleUserSignInDomainModel?,
    ): Outcome<MindplexDomainError, Unit> {
        if (!connectivityRepositoryContract.isConnected()) {
            return Outcome.failure(
                error = MindplexDomainError.NETWORK,
            )
        }
        val googleUserDomainModel = params ?: return Outcome.failure(
            error = MindplexDomainError.OTHER,
        )

        return jwtHandlerContract
            .decodeSubject(googleUserDomainModel.userId)
            .unwrap(
                onFailure = { Outcome.failure(MindplexDomainError.OTHER) },
                onSuccess = { userId ->
                    val googleUser = googleUserDomainModel.copy(
                        userId = userId,
                        profilePictureUrl = profileImageInterceptorContract.intercept(
                            googleUserDomainModel.profilePictureUrl,
                        ),
                        countryCode = countryCodeRepositoryContract.fetchCountryCode().getOrNull(),
                    )
                    signInNetworkRepositoryContract.signIn(googleUser)
                    signInPreferencesRepositoryContract.signIn(googleUser.userId)

                    Outcome.success(Unit)
                },
            )
    }
}
