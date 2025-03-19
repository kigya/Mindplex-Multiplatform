package dev.kigya.mindplex.core.data.parser.handler

import dev.kigya.mindplex.core.data.parser.JwtParser
import parser.contract.JwtHandlerContract
import dev.kigya.mindplex.core.util.dsl.runSuspendCatching
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.jsonPrimitive

class JwtHandler(
    private val dispatcher: CoroutineDispatcher,
) : JwtHandlerContract {
    override suspend fun decodeSubject(jwtToken: String): Result<String> = withContext(dispatcher) {
        runSuspendCatching {
            requireNotNull(
                requireNotNull(JwtParser.parseToJsonObject(jwtToken)) { "Invalid JWT token" }
                    ["sub"]?.jsonPrimitive?.content,
            ) { "Subject not found in JWT token" }
        }
    }
}
