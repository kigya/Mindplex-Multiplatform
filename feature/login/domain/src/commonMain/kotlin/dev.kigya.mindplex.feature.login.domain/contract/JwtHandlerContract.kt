package dev.kigya.mindplex.feature.login.domain.contract

interface JwtHandlerContract {
    suspend fun decodeSubject(jwtToken: String): Result<String>
}
