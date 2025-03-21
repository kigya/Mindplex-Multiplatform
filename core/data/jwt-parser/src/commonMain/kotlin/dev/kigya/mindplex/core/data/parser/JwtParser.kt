package dev.kigya.mindplex.core.data.parser

import kotlinx.serialization.json.JsonObject

expect class JwtParser() : JwtParserContract {
    override fun parseToJsonObject(jwtToken: String): JsonObject?
}

internal object JWT {
    const val PAYLOAD = 1
    const val PARTS = 3
}

interface JwtParserContract {
    fun parseToJsonObject(jwtToken: String): JsonObject?
}
