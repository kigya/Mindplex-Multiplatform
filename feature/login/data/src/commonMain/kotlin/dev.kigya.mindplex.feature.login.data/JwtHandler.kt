package dev.kigya.mindplex.feature.login.data

import dev.kigya.mindplex.core.data.parser.JwtParserContract
import dev.kigya.mindplex.feature.login.domain.contract.JwtHandlerContract
import dev.kigya.outcome.Outcome
import dev.kigya.outcome.outcomeSuspendCatchingOn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.json.jsonPrimitive

class JwtHandler(
    private val dispatcher: CoroutineDispatcher,
    private val jwtParserContract: JwtParserContract,
) : JwtHandlerContract {
    override suspend fun decodeSubject(jwtToken: String): Outcome<*, String> =
        outcomeSuspendCatchingOn(dispatcher) {
            requireNotNull(
                requireNotNull(jwtParserContract.parseToJsonObject(jwtToken)) { "Invalid JWT token" }
                    ["sub"]?.jsonPrimitive?.content,
            ) { "Subject not found in JWT token" }
        }
}
