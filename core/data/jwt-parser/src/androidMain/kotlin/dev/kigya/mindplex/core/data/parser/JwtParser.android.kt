package dev.kigya.mindplex.core.data.parser

import kotlinx.serialization.json.JsonObject
import android.util.Base64
import kotlinx.serialization.json.Json
import java.nio.charset.StandardCharsets

actual object JwtParser {

    actual fun parseToJsonObject(jwtToken: String): JsonObject? {
        return getPayload(jwtToken)
    }

    private fun getPayload(jwt: String): JsonObject? {
        val jwtParts = jwt.split("\\.".toRegex()).toTypedArray()
        try {
            if (jwtParts.size != JWT.PARTS) {
                throw Exception()
            }
            val payload = jwt.split("\\.".toRegex()).toTypedArray()[JWT.PAYLOAD]
            val sectionDecoded = Base64.decode(payload.toByteArray(), Base64.URL_SAFE)
            val jwtSection = String(sectionDecoded, StandardCharsets.UTF_8)
            return Json.decodeFromString<JsonObject>(jwtSection)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}
