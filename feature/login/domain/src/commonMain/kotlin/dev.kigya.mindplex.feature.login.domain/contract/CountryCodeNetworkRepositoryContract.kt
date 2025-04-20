package dev.kigya.mindplex.feature.login.domain.contract

import dev.kigya.outcome.Outcome

interface CountryCodeNetworkRepositoryContract {
    suspend fun fetchCountryCode(): Outcome<*, String>
    suspend fun updateCountryCode(
        userId: String,
        countryCode: String,
    )
}
