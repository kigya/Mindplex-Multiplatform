package dev.kigya.mindplex.feature.login.data

import io.ktor.client.HttpClient

expect class HttpClientProvider() {
    fun create(): HttpClient
}
