package dev.kigya.mindplex.core.data.parser

import exception.InvalidJwtStructureException
import exception.JsonParsingException
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.serialization.json.JsonObject
import platform.Foundation.NSData
import platform.Foundation.NSJSONSerialization
import platform.Foundation.NSString
import platform.Foundation.componentsSeparatedByString
import platform.Foundation.create
import platform.Foundation.stringByPaddingToLength

actual class JwtParser : JwtParserContract {
    actual override fun parseToJsonObject(jwtToken: String): JsonObject? =
        getPayload(jwtToken as NSString)

    private fun getPayload(jwt: NSString): JsonObject? {
        val jwtParts = jwt.componentsSeparatedByString(".") as List<NSString>
        try {
            if (jwtParts.size != JWT.PARTS) {
                throw InvalidJwtStructureException(
                    "Invalid JWT structure: expected ${JWT.PARTS} parts but found ${jwtParts.size}.",
                )
            }
            return decodeJWTPart(jwtParts[JWT.PAYLOAD]).toJsonObject()
        } catch (e: InvalidJwtStructureException) {
            throw JsonParsingException("Error parsing JWT: Invalid structure", e)
        }
        return null
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun decodeJWTPart(value: NSString): Map<String, Any> {
        val bodyData = base64Decode(value) ?: return mapOf()
        return NSJSONSerialization.JSONObjectWithData(bodyData, 0u, null) as Map<String, Any>
    }

    private fun base64Decode(base64: NSString): NSData? {
        val padding = base64.stringByPaddingToLength(
            newLength = (base64.length + 3u) / 4u * 4u,
            withString = "=",
            startingAtIndex = 0u,
        )
        return NSData.create(base64Encoding = padding)
    }
}
