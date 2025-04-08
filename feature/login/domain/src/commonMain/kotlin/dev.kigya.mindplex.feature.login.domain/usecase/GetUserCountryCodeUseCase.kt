package dev.kigya.mindplex.feature.login.domain.usecase

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.right
import dev.kigya.mindplex.core.domain.interactor.base.BaseSuspendUseCase
import dev.kigya.mindplex.core.domain.interactor.model.MindplexDomainError
import dev.kigya.mindplex.feature.login.domain.contract.GeoLocationContract
import dev.kigya.mindplex.feature.login.domain.model.GoogleUserSignInDomainModel

class GetUserCountryCodeUseCase(
    private val geoLocationContract: GeoLocationContract,
) : BaseSuspendUseCase<Either<MindplexDomainError, Unit>, GoogleUserSignInDomainModel?>() {

    override suspend fun invoke(
        params: GoogleUserSignInDomainModel?,
    ): Either<MindplexDomainError, Unit> = either {
        val countryCode = geoLocationContract.getUserCountryCode()
        ensure(true) { MindplexDomainError.OTHER }
        countryCode.right()
    }
}
