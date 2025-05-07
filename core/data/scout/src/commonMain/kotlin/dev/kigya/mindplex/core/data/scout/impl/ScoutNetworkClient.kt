package dev.kigya.mindplex.core.data.scout.impl

import dev.kigya.mindplex.core.data.scout.api.ScoutNetworkClientContract
import dev.kigya.mindplex.core.data.scout.exception.ScoutException
import dev.kigya.mindplex.core.util.JwtProvider
import dev.kigya.mindplex.core.util.buildstage.BuildStageContract
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readRawBytes
import io.ktor.http.URLProtocol
import io.ktor.http.isSuccess
import io.ktor.http.path
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.reflect.KType
import kotlin.reflect.typeOf

class ScoutNetworkClient(
    private val httpClient: HttpClient,
    private val buildStageContract: BuildStageContract,
    private val jwtProvider: JwtProvider,
    private val dispatcher: CoroutineDispatcher,
) : ScoutNetworkClientContract {

    @Suppress("UNCHECKED_CAST")
    override suspend fun <ResponseType : Any> get(
        vararg path: String,
        params: Map<String, String>,
        headers: Array<ScoutHeaders>,
        type: KType,
    ): ResponseType = withContext(dispatcher) {
        val requestHeaders = resolveHeaders(headers).toMutableMap().apply {
            put(HEADER_X_STAGE, buildStageContract.getStage())
        }

        val response: HttpResponse = httpClient.get {
            url {
                protocol = URLProtocol.HTTPS
                host = BASE_PATH
                path(*path)
                params.forEach { (key, value) ->
                    parameters.append(key, value)
                }
            }
            requestHeaders.forEach { (key, value) ->
                header(key, value)
            }
        }

        if (response.status.isSuccess()) {
            val text = response.readRawBytes().decodeToString()
            if (type == typeOf<String>()) {
                text as ResponseType
            } else {
                Json.decodeFromString(
                    deserializer = serializer(type),
                    string = text,
                ) as ResponseType
            }
        } else {
            throw ScoutException.FailedToFetchData(response.status.description)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override suspend fun <ResponseType : Any> post(
        vararg path: String,
        params: Map<String, String>,
        headers: Array<ScoutHeaders>,
        body: Any,
        type: KType,
    ): ResponseType = withContext(dispatcher) {
        val requestHeaders = resolveHeaders(headers).toMutableMap().apply {
            put(HEADER_X_STAGE, buildStageContract.getStage())
        }

        val response: HttpResponse = httpClient.post {
            url {
                protocol = URLProtocol.HTTPS
                host = BASE_PATH
                path(*path)
                params.forEach { (key, value) ->
                    parameters.append(key, value)
                }
            }
            requestHeaders.forEach { (key, value) ->
                header(key, value)
            }
            setBody(body)
        }

        if (response.status.isSuccess()) {
            val text = response.readRawBytes().decodeToString()
            if (type == typeOf<String>()) {
                text as ResponseType
            } else {
                Json.decodeFromString(
                    deserializer = serializer(type),
                    string = text,
                ) as ResponseType
            }
        } else {
            throw ScoutException.FailedToFetchData(response.status.description)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override suspend fun <ResponseType : Any> patch(
        vararg path: String,
        params: Map<String, String>,
        headers: Array<ScoutHeaders>,
        body: Any,
        type: KType,
    ): ResponseType = withContext(dispatcher) {
        val requestHeaders = resolveHeaders(headers).toMutableMap().apply {
            put(HEADER_X_STAGE, buildStageContract.getStage())
        }

        val response: HttpResponse = httpClient.patch {
            url {
                protocol = URLProtocol.HTTPS
                host = BASE_PATH
                path(*path)
                params.forEach { (key, value) ->
                    parameters.append(key, value)
                }
            }
            requestHeaders.forEach { (key, value) ->
                header(key, value)
            }
            setBody(body)
        }

        if (response.status.isSuccess()) {
            val text = response.readRawBytes().decodeToString()
            if (type == typeOf<String>()) {
                text as ResponseType
            } else {
                Json.decodeFromString(
                    deserializer = serializer(type),
                    string = text,
                ) as ResponseType
            }
        } else {
            throw ScoutException.FailedToFetchData(response.status.description)
        }
    }

    private suspend fun resolveHeaders(headers: Array<ScoutHeaders>): Map<String, String> {
        val result = mutableMapOf<String, String>()
        result[HEADER_X_STAGE] = buildStageContract.getStage()

        headers.forEach {
            when (it) {
                is ScoutHeaders.GoogleJwt -> result[HEADER_AUTHORIZATION] = "$BEARER_PREFIX ${it.value}"

                is ScoutHeaders.MindplexJwt -> {
                    val jwt = jwtProvider.getToken()
                    result[HEADER_AUTHORIZATION] = "$BEARER_PREFIX $jwt"
                }
            }
        }
        return result
    }

    private companion object {
        const val BASE_PATH = "mindplex-backend.onrender.com"
        const val HEADER_X_STAGE = "X-Stage"
        const val BEARER_PREFIX = "Bearer"
        const val HEADER_AUTHORIZATION = "Authorization"
    }
}
