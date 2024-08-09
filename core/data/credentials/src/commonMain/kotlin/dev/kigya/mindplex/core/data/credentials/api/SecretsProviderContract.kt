package dev.kigya.mindplex.core.data.credentials.api

interface SecretsProviderContract {
    suspend fun provideApiKey(name: String): Result<String>
}
