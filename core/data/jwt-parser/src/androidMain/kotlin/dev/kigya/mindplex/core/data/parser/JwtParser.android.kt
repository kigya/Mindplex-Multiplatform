package dev.kigya.mindplex.core.data.parser

import android.util.Base64
import exception.Base64DecodingException
import exception.InvalidJwtStructureException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import java.nio.charset.StandardCharsets

actual class JwtParser : JwtParserContract {

    actual override fun parseToJsonObject(jwtToken: String): JsonObject? = getPayload(jwtToken)

    private fun getPayload(jwt: String): JsonObject? {
        val jwtParts = jwt.split(JWT_DELIMITER.toRegex()).toTypedArray()
        try {
            if (jwtParts.size != JWT.PARTS) {
                throw InvalidJwtStructureException(
                    "Error: JWT token has invalid number of parts. Expecting" +
                        "Part ${JWT.PARTS}, but received ${jwtParts.size}.",
                )
            }
            val payload = jwt.split(JWT_DELIMITER.toRegex()).toTypedArray()[JWT.PAYLOAD]
            val sectionDecoded = Base64.decode(payload.toByteArray(), Base64.URL_SAFE)
            val jwtSection = String(sectionDecoded, StandardCharsets.UTF_8)
            return Json.decodeFromString<JsonObject>(jwtSection)
        } catch (e: IllegalArgumentException) {
            throw Base64DecodingException("Error: Failed to decode Base64 payload.", e)
        }
        return null
    }

    private companion object {
        private const val JWT_DELIMITER = "\\."
    }
}
