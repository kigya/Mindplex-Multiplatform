package dev.kigya.mindplex.core.data.scout.api

import kotlin.reflect.KType
import kotlin.reflect.typeOf

interface ScoutNetworkClientContract {
    suspend fun <ResponseType : Any> fetch(
        vararg path: String,
        params: Map<String, String> = emptyMap(),
        type: KType,
    ): ResponseType
}

suspend inline fun <reified T : Any> ScoutNetworkClientContract.fetchReified(
    vararg path: String,
    params: Map<String, String> = emptyMap(),
): T = fetch(
    path = path,
    params = params,
    type = typeOf<T>(),
)
