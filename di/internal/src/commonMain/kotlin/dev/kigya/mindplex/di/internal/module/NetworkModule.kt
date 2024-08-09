package dev.kigya.mindplex.di.internal.module

import dev.kigya.mindplex.core.data.credentials.api.SecretsProviderContract
import dev.kigya.mindplex.core.data.credentials.internal.SecretsProvider
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val networkModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    },
                )
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Napier.v(tag = "NetworkLog", message = message)
                    }
                }
                level = LogLevel.ALL
            }
        }.also { Napier.base(DebugAntilog()) }
    }

    single {
        SecretsProvider(
            dispatcher = get(qualifier = named(Dispatchers.IO::class.simpleName.orEmpty())),
        )
    } bind SecretsProviderContract::class
}
