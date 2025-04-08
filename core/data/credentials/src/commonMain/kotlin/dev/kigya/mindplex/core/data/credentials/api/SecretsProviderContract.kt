package dev.kigya.mindplex.core.data.credentials.api

interface SecretsProviderContract {
    suspend fun provideFlagsKey(): Result<String>
}
