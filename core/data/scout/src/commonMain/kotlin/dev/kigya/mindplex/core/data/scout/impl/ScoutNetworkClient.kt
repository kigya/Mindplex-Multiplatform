package dev.kigya.mindplex.core.data.scout.impl

import dev.kigya.mindplex.core.data.scout.api.ScoutNetworkClientContract
import dev.kigya.mindplex.core.data.scout.exception.ScoutException
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
    private val dispatcher: CoroutineDispatcher,
) : ScoutNetworkClientContract {

    @Suppress("UNCHECKED_CAST")
    override suspend fun <ResponseType : Any> get(
        vararg path: String,
        params: Map<String, String>,
        headers: Map<String, String>,
        type: KType,
    ): ResponseType = withContext(dispatcher) {
        val response: HttpResponse = httpClient.get {
            url {
                protocol = URLProtocol.HTTPS
                host = BASE_PATH
                path(*path)
                params.forEach { (key, value) ->
                    parameters.append(key, value)
                }
            }
            headers.forEach { (key, value) ->
                header(key, value)
            }
        }
        if (response.status.isSuccess()) {
            val bytes = response.readRawBytes()
            val text = bytes.decodeToString()
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
        headers: Map<String, String>,
        body: Any,
        type: KType,
    ): ResponseType = withContext(dispatcher) {
        val response: HttpResponse = httpClient.post {
            url {
                protocol = URLProtocol.HTTPS
                host = BASE_PATH
                path(*path)
                params.forEach { (key, value) ->
                    parameters.append(key, value)
                }
            }
            headers.forEach { (key, value) ->
                header(key, value)
            }
            setBody(body)
        }
        if (response.status.isSuccess()) {
            val bytes = response.readRawBytes()
            val text = bytes.decodeToString()
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
        headers: Map<String, String>,
        body: Any,
        type: KType,
    ): ResponseType = withContext(dispatcher) {
        val response: HttpResponse = httpClient.patch {
            url {
                protocol = URLProtocol.HTTPS
                host = BASE_PATH
                path(*path)
                params.forEach { (key, value) ->
                    parameters.append(key, value)
                }
            }
            headers.forEach { (key, value) ->
                header(key, value)
            }
            setBody(body)
        }
        if (response.status.isSuccess()) {
            val bytes = response.readRawBytes()
            val text = bytes.decodeToString()
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

    private companion object {
        const val BASE_PATH = "mindplex-backend.onrender.com"
    }
}
