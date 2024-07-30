package dev.kigya.mindplex.feature.login.data.repository.handler

import com.wonddak.jwt.JwtParser
import dev.kigya.mindplex.core.util.dsl.runSuspendCatching
import dev.kigya.mindplex.feature.login.domain.contract.JwtHandlerContract
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
