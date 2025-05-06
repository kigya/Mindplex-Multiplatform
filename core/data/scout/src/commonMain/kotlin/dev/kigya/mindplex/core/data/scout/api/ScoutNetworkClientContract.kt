package dev.kigya.mindplex.core.data.scout.api

import dev.kigya.mindplex.core.data.scout.impl.ScoutHeaders
import kotlin.reflect.KType
import kotlin.reflect.typeOf

interface ScoutNetworkClientContract {
    suspend fun <ResponseType : Any> get(
        vararg path: String,
        params: Map<String, String> = emptyMap(),
        headers: Array<ScoutHeaders> = emptyArray(),
        type: KType,
    ): ResponseType

    suspend fun <ResponseType : Any> post(
        vararg path: String,
        params: Map<String, String> = emptyMap(),
        headers: Array<ScoutHeaders> = emptyArray(),
        body: Any,
        type: KType,
    ): ResponseType

    suspend fun <ResponseType : Any> patch(
        vararg path: String,
        params: Map<String, String> = emptyMap(),
        headers: Array<ScoutHeaders> = emptyArray(),
        body: Any,
        type: KType,
    ): ResponseType
}

suspend inline fun <reified T : Any> ScoutNetworkClientContract.getReified(
    vararg path: String,
    params: Map<String, String> = emptyMap(),
    headers: Array<ScoutHeaders> = emptyArray(),
): T = get(
    path = path,
    params = params,
    headers = headers,
    type = typeOf<T>(),
)

suspend inline fun <reified T : Any> ScoutNetworkClientContract.postReified(
    vararg path: String,
    params: Map<String, String> = emptyMap(),
    headers: Array<ScoutHeaders> = emptyArray(),
    body: Any,
): T = post(
    path = path,
    params = params,
    headers = headers,
    body = body,
    type = typeOf<T>(),
)

suspend inline fun <reified T : Any> ScoutNetworkClientContract.patchReified(
    vararg path: String,
    params: Map<String, String> = emptyMap(),
    headers: Array<ScoutHeaders> = emptyArray(),
    body: Any,
): T = patch(
    path = path,
    params = params,
    headers = headers,
    body = body,
    type = typeOf<T>(),
)
