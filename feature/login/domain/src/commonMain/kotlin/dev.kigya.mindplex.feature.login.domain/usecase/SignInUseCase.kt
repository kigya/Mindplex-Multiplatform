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
    private val signInPreferencesRepository: SignInPreferencesRepositoryContract,
    private val signInNetworkRepository: SignInNetworkRepositoryContract,
    private val countryCodeRepository: CountryCodeNetworkRepositoryContract,
    private val profileImageInterceptor: ProfileImageInterceptorContract,
    private val jwtHandler: JwtHandlerContract,
    private val connectivity: ConnectivityRepositoryContract,
) : BaseSuspendUseCase<Outcome<MindplexDomainError, Unit>, GoogleUserSignInDomainModel?>() {

    @CheckResult
    override suspend fun invoke(
        params: GoogleUserSignInDomainModel?,
    ): Outcome<MindplexDomainError, Unit> {
        if (!connectivity.isConnected()) return Outcome.failure(MindplexDomainError.NETWORK)
        val googleUserDomainModel = params ?: return Outcome.failure(MindplexDomainError.OTHER)

        return jwtHandler
            .decodeSubject(googleUserDomainModel.tokenId)
            .unwrap(
                onFailure = { Outcome.failure(MindplexDomainError.OTHER) },
                onSuccess = { tokenId ->
                    val googleUser = googleUserDomainModel.copy(
                        tokenId = tokenId,
                        profilePictureUrl = profileImageInterceptor.intercept(googleUserDomainModel.profilePictureUrl),
                        countryCode = countryCodeRepository.fetchCountryCode().getOrNull(),
                    )
                    signInNetworkRepository.signIn(googleUser)
                    signInPreferencesRepository.signIn(googleUser.tokenId)

                    Outcome.success(Unit)
                },
            )
    }
}
