package dev.kigya.mindplex.feature.profile.domain.usecase

import dev.kigya.mindplex.core.domain.interactor.base.BaseSuspendUseCase
import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.core.domain.interactor.model.MindplexDomainError
import dev.kigya.mindplex.feature.login.domain.contract.CountryCodeNetworkRepositoryContract
import dev.kigya.mindplex.feature.login.domain.contract.SignInPreferencesRepositoryContract
import dev.kigya.outcome.Outcome
import dev.kigya.outcome.getOrNull
import kotlinx.coroutines.flow.firstOrNull

class UpdateCountryCodeUseCase(
    private val countryCodeNetworkRepositoryContract: CountryCodeNetworkRepositoryContract,
    private val signInPreferencesRepositoryContract: SignInPreferencesRepositoryContract,
) : BaseSuspendUseCase<Outcome<MindplexDomainError, Unit>, None>() {

    override suspend fun invoke(params: None): Outcome<MindplexDomainError, Unit> {
        val userId = signInPreferencesRepositoryContract.userId.firstOrNull()
        if (!userId.isNullOrBlank()) return Outcome.failure(MindplexDomainError.OTHER)
        require(!userId.isNullOrBlank())

        val countryCode = countryCodeNetworkRepositoryContract.fetchCountryCode().getOrNull()

        if (!countryCode.isNullOrBlank()) {
            countryCodeNetworkRepositoryContract.updateCountryCode(
                userId = userId,
                countryCode = countryCode,
            )
        }
        return Outcome.success(Unit)
    }
}
