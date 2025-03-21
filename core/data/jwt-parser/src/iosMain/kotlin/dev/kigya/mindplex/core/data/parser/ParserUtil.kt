package dev.kigya.mindplex.core.data.parser

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

internal fun Any?.toJsonElement(): JsonElement = when (this) {
    is Number -> JsonPrimitive(this)
    is Boolean -> JsonPrimitive(this)
    is String -> JsonPrimitive(this)
    is Array<*> -> JsonArray(this.map { it.toJsonElement() })
    is List<*> -> JsonArray(this.map { it.toJsonElement() })
    is Map<*, *> -> this.toJsonObject()
    is JsonElement -> this
    else -> JsonNull
}

internal fun Map<*, *>.toJsonObject(): JsonObject {
    val map = mutableMapOf<String, JsonElement>()
    this.forEach {
        if (it.key is String) {
            map[it.key as String] = it.value.toJsonElement()
        }
    }
    return JsonObject(map)
}
