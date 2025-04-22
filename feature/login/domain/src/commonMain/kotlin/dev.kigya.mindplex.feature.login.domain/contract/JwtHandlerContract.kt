package dev.kigya.mindplex.feature.login.domain.contract

import dev.kigya.outcome.Outcome

interface JwtHandlerContract {
    suspend fun decodeSubject(jwtToken: String): Outcome<*, String>
}
