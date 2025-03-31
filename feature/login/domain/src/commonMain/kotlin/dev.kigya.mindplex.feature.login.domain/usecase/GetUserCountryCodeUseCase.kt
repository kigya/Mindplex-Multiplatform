package dev.kigya.mindplex.feature.login.domain.usecase

import dev.kigya.mindplex.feature.login.domain.contract.GeoLocationContract

class GetUserCountryCodeUseCase(
    private val geoLocationContract: GeoLocationContract,
) {
    suspend operator fun invoke(): String? = geoLocationContract.getUserCountryCode()
}
