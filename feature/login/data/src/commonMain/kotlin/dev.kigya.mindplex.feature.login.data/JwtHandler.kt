package dev.kigya.mindplex.feature.login.data

import dev.kigya.mindplex.core.data.parser.JwtParserContract
import dev.kigya.mindplex.core.util.dsl.runSuspendCatching
import dev.kigya.mindplex.feature.login.domain.contract.JwtHandlerContract
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.jsonPrimitive

class JwtHandler(
    private val dispatcher: CoroutineDispatcher,
    private val jwtParserContract: JwtParserContract,
) : JwtHandlerContract {
    override suspend fun decodeSubject(jwtToken: String): Result<String> = withContext(dispatcher) {
        runSuspendCatching {
            requireNotNull(
                requireNotNull(jwtParserContract.parseToJsonObject(jwtToken)) { "Invalid JWT token" }
                    ["sub"]?.jsonPrimitive?.content,
            ) { "Subject not found in JWT token" }
        }
    }
}
