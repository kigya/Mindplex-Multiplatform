package dev.kigya.mindplex.feature.login.domain.contract

interface GeoLocationContract {
    suspend fun getUserCountryCode(): Result<String?>
}
