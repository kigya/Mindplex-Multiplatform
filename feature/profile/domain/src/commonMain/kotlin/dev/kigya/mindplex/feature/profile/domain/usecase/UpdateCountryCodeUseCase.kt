package dev.kigya.mindplex.feature.profile.domain.usecase

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import dev.kigya.mindplex.core.domain.interactor.base.BaseSuspendUseCase
import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.core.domain.interactor.model.MindplexDomainError
import dev.kigya.mindplex.feature.login.domain.contract.GeoLocationContract
import dev.kigya.mindplex.feature.login.domain.contract.SignInNetworkRepositoryContract
import dev.kigya.mindplex.feature.login.domain.contract.SignInPreferencesRepositoryContract
import kotlinx.coroutines.flow.firstOrNull

class UpdateCountryCodeUseCase(
    private val geoLocationContract: GeoLocationContract,
    private val signInPreferencesRepositoryContract: SignInPreferencesRepositoryContract,
    private val signInNetworkRepositoryContract: SignInNetworkRepositoryContract,
) : BaseSuspendUseCase<Either<MindplexDomainError, Unit>, None>() {

    override suspend fun invoke(params: None): Either<MindplexDomainError, Unit> = either {
        val userToken = signInPreferencesRepositoryContract.userToken.firstOrNull()
        ensure(!userToken.isNullOrBlank()) { MindplexDomainError.OTHER }

        val countryCode = geoLocationContract.getUserCountryCode().getOrNull()

        if (countryCode.isNullOrBlank()) {
            return@either
        }
        signInNetworkRepositoryContract.sendUserCountryCode(userToken, countryCode)
    }
}
