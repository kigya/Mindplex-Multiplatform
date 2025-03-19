package dev.kigya.mindplex.core.data.parser

import kotlinx.serialization.json.JsonObject

expect object JwtParser {

    fun parseToJsonObject(jwtToken: String): JsonObject?
}

object JWT {
    const val HEADER = 0
    const val PAYLOAD = 1
    const val SIGNATURE = 2
    const val PARTS = 3
}
