package dev.kigya.mindplex.feature.login.domain.usecase

import androidx.annotation.CheckResult
import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.right
import dev.kigya.mindplex.core.domain.connectivity.contract.ConnectivityRepositoryContract
import dev.kigya.mindplex.core.domain.interactor.base.BaseSuspendUseCase
import dev.kigya.mindplex.core.domain.interactor.model.MindplexDomainError
import dev.kigya.mindplex.feature.login.domain.contract.GeoLocationContract
import dev.kigya.mindplex.feature.login.domain.contract.JwtHandlerContract
import dev.kigya.mindplex.feature.login.domain.contract.ProfileImageInterceptorContract
import dev.kigya.mindplex.feature.login.domain.contract.SignInNetworkRepositoryContract
import dev.kigya.mindplex.feature.login.domain.contract.SignInPreferencesRepositoryContract
import dev.kigya.mindplex.feature.login.domain.model.GoogleUserSignInDomainModel

class SignInUseCase(
    private val signInPreferencesRepositoryContract: SignInPreferencesRepositoryContract,
    private val signInNetworkRepositoryContract: SignInNetworkRepositoryContract,
    private val profileImageInterceptor: ProfileImageInterceptorContract,
    private val jwtHandlerContract: JwtHandlerContract,
    private val connectivityRepositoryContract: ConnectivityRepositoryContract,
    private val geoLocationContract: GeoLocationContract,
) : BaseSuspendUseCase<Either<MindplexDomainError, Unit>, GoogleUserSignInDomainModel?>() {

    @CheckResult
    override suspend fun invoke(
        params: GoogleUserSignInDomainModel?,
    ): Either<MindplexDomainError, Unit> = either {
        ensure(connectivityRepositoryContract.isConnected()) { MindplexDomainError.NETWORK }
        requireNotNull(params) { raise(MindplexDomainError.OTHER) }

        val userIdResult = jwtHandlerContract.decodeSubject(params.tokenId)
        userIdResult.fold(
            onSuccess = { userId ->
                val countryCode = geoLocationContract.getUserCountryCode().getOrNull()

                val user = params.copy(
                    tokenId = userId,
                    profilePictureUrl = profileImageInterceptor.intercept(params.profilePictureUrl),
                    countryCode = countryCode,
                )
                signInNetworkRepositoryContract.signIn(user)
                signInPreferencesRepositoryContract.signIn(user.tokenId)
                Unit.right()
            },
            onFailure = { raise(MindplexDomainError.OTHER) },
        )
    }
}
